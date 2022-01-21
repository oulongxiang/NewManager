package com.tul.manage.warning.entity;

import apps.commons.util.tool_util.VerifyParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 工龄销量预警数据表(用于保存从调用18存储过程获取的工龄销量预警数据)
 *
 * @author Automatic Generation
 * @date 2021-11-04 14:31:29
 */
@Data
@TableName("seniority_warn")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "工龄销量预警数据表(用于保存从调用18存储过程获取的工龄销量预警数据)")
public class SeniorityWarn extends Model<SeniorityWarn> {
    private static final long serialVersionUID=1L;

    /**
     * id **主键**
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="id **主键**")
    private String id;

    /**
     * 风险信息表id **外键** 对应表【risk_info】字段 【id】
     */
    @ApiModelProperty(value="风险信息表id **外键** 对应表【risk_info】字段 【id】")
    @TableField("risk_info_id")
    private String riskInfoId;

    /**
     * 区域名称
     */
    @ApiModelProperty(value="区域名称")
    @TableField("org_name")
    private String orgName;

    /**
     * 代表姓名
     */
    @ApiModelProperty(value="代表姓名")
    @TableField("name")
    private String name;

    /**
     * 代表上级姓名
     */
    @ApiModelProperty(value="代表上级姓名")
    @TableField("superior_name")
    private String superiorName;

    /**
     * 代表岗位
     */
    @ApiModelProperty(value="代表岗位")
    @TableField("post_name")
    private String postName;

    /**
     * 代表工龄
     */
    @ApiModelProperty(value="代表工龄")
    @TableField("working_age")
    private Float workingAge;

    /**
     * X-12月销量
     */
    @ApiModelProperty(value="X-12月销量")
    @TableField("x12")
    private BigDecimal x12;

    /**
     * X-11月销量
     */
    @ApiModelProperty(value="X-11月销量")
    @TableField("x11")
    private BigDecimal x11;

    /**
     * X-10月销量
     */
    @ApiModelProperty(value="X-10月销量")
    @TableField("x10")
    private BigDecimal x10;

    /**
     * X-9月销量
     */
    @ApiModelProperty(value="X-9月销量")
    @TableField("x9")
    private BigDecimal x9;

    /**
     * X-8月销量
     */
    @ApiModelProperty(value="X-8月销量")
    @TableField("x8")
    private BigDecimal x8;

    /**
     * X-7月销量
     */
    @ApiModelProperty(value="X-7月销量")
    @TableField("x7")
    private BigDecimal x7;

    /**
     * X-6月销量
     */
    @ApiModelProperty(value="X-6月销量")
    @TableField("x6")
    private BigDecimal x6;

    /**
     * X-5月销量
     */
    @ApiModelProperty(value="X-5月销量")
    @TableField("x5")
    private BigDecimal x5;

    /**
     * X-4月销量
     */
    @ApiModelProperty(value="X-4月销量")
    @TableField("x4")
    private BigDecimal x4;

    /**
     * X-3月销量
     */
    @ApiModelProperty(value="X-3月销量")
    @TableField("x3")
    private BigDecimal x3;

    /**
     * X-2月销量
     */
    @ApiModelProperty(value="X-2月销量")
    @TableField("x2")
    private BigDecimal x2;

    /**
     * X-1月销量
     */
    @ApiModelProperty(value="X-1月销量")
    @TableField("x1")
    private BigDecimal x1;

    /**
     * 本月销量
     */
    @ApiModelProperty(value="本月销量")
    @TableField("x0")
    private BigDecimal x0;

    /**
     * 获取时间
     */
    @ApiModelProperty(value="获取时间")
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 交换列表存储过程映射列表
     * 将【18】库存储过程【Temp_SJ_Get_EmpGLSale_YJ】执行的结果 映射成列表
     *
     * @param captureWaringSale 【18】库存储过程【Temp_SJ_Get_EmpGLSale_YJ】执行的结果
     * @param riskInfoId        风险信息id
     * @param createTime        创建时间
     * @return {@link List}<{@link SeniorityWarn}>
     */
    public static List<SeniorityWarn> swapListToStoredProcedureMapList(List<Map<String, Object>> captureWaringSale,String riskInfoId,LocalDateTime createTime){
        if(IterUtil.isEmpty(captureWaringSale)){
            return new LinkedList<>();
        }
        VerifyParams.throwStrBlank(riskInfoId,"风险信息id为空");
        //校验Map
        List<SeniorityWarn> result=new LinkedList<>();
        for (Map<String,Object> seniorityWarn : captureWaringSale) {
            SeniorityWarn save=new SeniorityWarn();
            save.setRiskInfoId(riskInfoId);
            save.setCreateTime(createTime);
            BeanUtil.fillBeanWithMap(seniorityWarn, save, false);
            result.add(save);
        }
        return result;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
