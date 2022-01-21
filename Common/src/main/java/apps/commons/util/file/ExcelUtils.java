package apps.commons.util.file;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * http://easypoi.mydoc.io/#text_231892 教程
 * excel 工具类
 *
 * @author YJH
 * @date 2019/12/30 13:07
 */
@Slf4j
public class ExcelUtils {


    // 每个工作表允许的最大数据量
    public static final int BIG_DATA_EXPORT_MIN = 300000;
    // 运行导出的最大数据量
    public static final int BIG_DATA_EXPORT_MAX = 10000000;


    /**
     * Excel导出
     *
     * @param list           数据
     * @param title          标题名 称
     * @param sheetName      工作表名称
     * @param pojoClass      实体映射
     * @param fileName       文件名 称
     * @param isCreateHeader 是否创建头部
     * @param response       响应
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    /**
     * Excel导出
     *
     * @param list      数据
     * @param title     标题名 称
     * @param sheetName 工作表名称
     * @param pojoClass 实体映射
     * @param fileName  文件名 称
     * @param response  响应
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * Excel导出，多个工作表
     *
     * @param list     工作表Map（title:ExportParams, entity:实体映射, data:list数据）
     * @param fileName 文件名 称
     * @param response 响应
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 默认导出
     *
     * @param list         数据
     * @param pojoClass    实体映射
     * @param fileName     文件名 称
     * @param response     响应
     * @param exportParams 模版参数对象
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
                                      ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);

        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 响应流文件下载
     *
     * @param fileName 文件名称
     * @param response 响应
     * @param workbook 工作簿
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Access-Control-Expose-Headers", "fileName");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 响应流文件下载
     *
     * @param list     工作表Map（title:ExportParams, entity:实体映射, data:list数据）
     * @param fileName 文件名称
     * @param response 响应
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
        return list;
    }

    /**
     * Excel导入
     *
     * @param file       Excel文件
     * @param titleRows  标题行数
     * @param headerRows 头部行数
     * @param pojoClass  实体映射
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("excel文件不能为空");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return list;
    }

    /**
     * Excel导入
     *
     * @param
     * @param titleRows  标题行数
     * @param headerRows 头部行数
     * @param pojoClass  实体映射
     * @return
     */
    public static <T> List<T> importExcel1(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
        return list;
    }

    /**
     * 老会员导出
     *
     * @param list      数据
     * @param pojoClass 实体映射
     * @param fileName  文件名 称
     * @param response  响应
     */
    public static void patientInfoExport(List<?> list, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {

        ExportParams exportParams = new ExportParams(null, sheetName);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        CellStyle redFontStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        redFontStyle.setFont(font);
        redFontStyle.setAlignment(HorizontalAlignment.CENTER);
        redFontStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 获取工作簿内容
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();

        while (sheetIterator.hasNext()) {
            Iterator<Row> rowIterator = sheetIterator.next().rowIterator();
            while (rowIterator.hasNext()) {
                // 非正常数据标红
                Row next = rowIterator.next();
                int physicalNumberOfCells = next.getPhysicalNumberOfCells();
                String rowState = next.getCell(29).getStringCellValue().trim();
                if ("数据备注".equals(rowState)) {
                    for (int i = 0; i < physicalNumberOfCells; i++) {
                        if (next.getCell(i).getStringCellValue().contains("*")) {
                            next.getCell(i).setCellStyle(redFontStyle);
                        }
                    }
                } else {
                    if (!"正常数据".equals(rowState)) {
                        for (int i = 0; i < physicalNumberOfCells; i++) {
                            next.getCell(i).setCellStyle(redFontStyle);
                        }
                    }
                }
            }
        }
        downLoadExcel(fileName, response, workbook);
    }

//    /**
//     * 动态生成excel 列
//     * @param ef
//     * @param entities
//     */
//    public static void dynamicExportEntity(List<ExportField> ef, List<ExcelExportEntity> entities, int width) {
//        //单元格的excel 表头
//        ef.forEach(item -> {
//            //需要合并单元格的表头
//            List<ExportField> children = item.getChildren();
//            if(!CollectionUtils.isEmpty(children)){
//                ExcelExportEntity parent = new ExcelExportEntity(item.getName(), item.getKey());
//                List<ExcelExportEntity> entitiesChildren = Lists.newArrayList();
//                children.forEach(e -> {
//                    entitiesChildren.add(new ExcelExportEntity(e.getName(), e.getKey(), width));
//                });
//                parent.setNeedMerge(true);
//                parent.setList(entitiesChildren);
//                entities.add(parent);
//            }else{
//                entities.add(new ExcelExportEntity(item.getName(), item.getKey(), width));
//            }
//        });
//    }

//    /**
//     * 组装excel 数据
//     * @param ef
//     * @param list
//     * @return  List<Map<String, Object>>
//     */
//    public static List<Map<String, Object>> dynamicListByKey(List<ExportField> ef, List<?> list) {
//        //最终的数据
//        List<Map<String, Object>> datas = new ArrayList<>();
//        Map<String, Object> map;
//        for (Object t : list) {
//            map = new HashMap<String, Object>();
//            for (int j = 0; j < ef.size(); j++) {
//                List<ExportField> children = ef.get(j).getChildren();
//                String filedName = ef.get(j).getKey();
//                if(!CollectionUtils.isEmpty(children)){
//                    //遍历需要合并单元格的子列
//                    traverseChildren(map, t, children, filedName);
//                }else if(StringUtils.isNotBlank(filedName)){
//                    map.put(filedName, Reflections.getFieldValue(t, filedName));
//                }
//            }
//            datas.add(map);
//        }
//        return datas;
//    }

//    /**
//     * 遍历需要合并单元格的子列
//     */
//    private static void traverseChildren(Map<String, Object> map, Object t, List<ExportField> children, String filedName) {
//        List<Map<String, Object>> childrenMaps = Lists.newArrayList();
//        Map<String, Object> childrenMap = new HashMap<String, Object>();
//        for (int k = 0; k < children.size(); k++) {
//            String childrenFiledName = children.get(k).getKey();
//            if(StringUtils.isNotBlank(childrenFiledName)){
//                childrenMap.put(childrenFiledName, Reflections.getFieldValue(t, childrenFiledName));
//            }
//        }
//        childrenMaps.add(childrenMap);
//        map.put(filedName, childrenMaps);
//    }

//    /**
//     * 动态导出生成excel
//     */
//    public static void exportDynamicExcel(String fileName, String title, String sheet, List<ExportField> exportField, List<?> list, int width, HttpServletResponse response) {
//        //生成动态列
//        List<ExcelExportEntity> entities = Lists.newArrayList();
//        dynamicExportEntity(exportField, entities, width);
//        //组装数据entities
//        List<Map<String, Object>> maps = dynamicListByKey(exportField, list);
//        exportExcel(fileName, new ExportParams(title, sheet), entities, maps, response);
//    }
//
//    public static void exportExcel(String fileName, ExportParams exportParams, List<ExcelExportEntity> entities, List<Map<String, Object>> list, HttpServletResponse response){
//        defaultExport(fileName, exportParams, entities, list, response);
//    }
//
//    private static void defaultExport(String fileName, ExportParams exportParams, List<ExcelExportEntity> entities, List<Map<String, Object>> list, HttpServletResponse response){
//        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entities, list);
//        if (workbook != null) {
//            downLoadExcel(fileName, response, workbook);
//        }
//    }


}
