package com.tul.manage.security.service.impl;

import apps.commons.entity.TulFileBean;
import apps.commons.util.TulFileUtil;
import apps.commons.util.tool_util.VerifyParams;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.AnnexDao;
import com.tul.manage.security.entity.Annex;
import com.tul.manage.security.enums.AnnexModuleEnum;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IAnnexService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-07-01
 */
@Service
@DS("Admin")
public class AnnexServiceImpl extends BaseServiceImpl<AnnexDao, Annex> implements IAnnexService {


    @Override
    public void uploadAnnexToFile(MultipartFile uploadFile, AnnexModuleEnum annexModule, String annexModuleId,boolean isCompress,int sort) {
        VerifyParams.throwEmpty(uploadFile,"上传文件为空!");
        VerifyParams.throwEmpty(annexModule,"上传模块为空!");
        VerifyParams.throwEmpty(annexModuleId,"上传模块为空!");
        Annex annex = new Annex();
        annex.setModuleName(annexModule);
        annex.setModuleId(annexModuleId);
        annex.setSort(sort);
        annex.setDownload(0);
        double sizeToMb=((double)uploadFile.getSize()/1048576);
        annex.setSize( NumberUtil.round(sizeToMb,2).floatValue());
        annex.setUploadFileName(uploadFile.getOriginalFilename());
        annex.setUploadUserId(getUserInfo().getId());
        if(Objects.requireNonNull(uploadFile.getOriginalFilename()).lastIndexOf(".")!=-1){
            String suffix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            if ("jpeg".equals(suffix) ||"png".equals(suffix) ||"bmp".equals(suffix) || "jpg".equals(suffix)){
                if(isCompress){
                    TulFileBean tulFileBean = TulFileUtil.uploadImage(uploadFile, annexModule.getUploadPath());
                    annex.setFileUrl(TulFileUtil.getFileCompleteUrl(tulFileBean.getOriginalPictureUrl()));
                    annex.setImgThumbUrl(TulFileUtil.getFileCompleteUrl(tulFileBean.getCompressedPictureUrl()));
                }else{
                    annex.setFileUrl(TulFileUtil.getFileCompleteUrl(TulFileUtil.uploadFile(uploadFile, annexModule.getUploadPath())));
                }
            }else{
                annex.setFileUrl(TulFileUtil.getFileCompleteUrl(TulFileUtil.uploadFile(uploadFile, annexModule.getUploadPath())));
            }
            annex.setExt(suffix);
        }else{
            annex.setFileUrl(TulFileUtil.getFileCompleteUrl(TulFileUtil.uploadFile(uploadFile, annexModule.getUploadPath())));
        }
        save(annex);
    }

    @Override
    public void delAnnexFile(String annexId) {
        VerifyParams.throwEmpty(annexId,"模块为空!");
        Annex annex = getById(annexId);
        VerifyParams.throwEmpty(annex,"该附件已删除");
        String fileUrl = annex.getFileUrl();
        VerifyParams.throwEmpty(fileUrl,"无法获取附件文件地址");
        if(!fileUrl.contains(TulFileUtil.DOMAIN)){
            VerifyParams.throwEmpty(fileUrl,"附件地址错误");
        }
        try {
            String delFileUrl = fileUrl.substring(fileUrl.indexOf(TulFileUtil.DOMAIN) + TulFileUtil.DOMAIN.length());
            TulFileUtil.delFile("//tulfile/"+delFileUrl);
        }catch (Exception ignored){}
        try{
            String imgThumbUrl = annex.getImgThumbUrl();
            if(!StrUtil.isBlank(imgThumbUrl) && imgThumbUrl.contains(TulFileUtil.DOMAIN)){
                String delFileUrl=imgThumbUrl.substring(imgThumbUrl.indexOf(TulFileUtil.DOMAIN)+TulFileUtil.DOMAIN.length());
                TulFileUtil.delFile("//tulfile/"+delFileUrl);
            }
        }catch (Exception ignored){}
        //删除数据库数据
        removeById(annex.getId());
    }
}