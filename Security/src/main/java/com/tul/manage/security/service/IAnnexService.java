package com.tul.manage.security.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Annex;
import com.tul.manage.security.enums.AnnexModuleEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-07-01
 */
public interface IAnnexService extends IService<Annex> {

    /**
     * 上传指定模块的附件
     * @param uploadFile 上传的文件
     * @param annexModule 附件所属模块
     * @param annexModuleId 模块id
     * @param isCompress 如果上传的为图片 是生成压缩图 true-是 false/null -否
     * @param sort 附件排序
     */
    void uploadAnnexToFile(MultipartFile uploadFile, AnnexModuleEnum annexModule, String annexModuleId,boolean isCompress,int sort);

    /**
     * 删除指定id的附件
     * @param annexId 附件id
     */
    void delAnnexFile(String annexId);



}
