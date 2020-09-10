package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 批次表(InvenBach)实体类
 *
 * @author jxj
 * @since 2020-09-10 19:01:37
 */
@Data
@TableName("t_invent_batch")
public class InventBatch implements Serializable {
    private static final long serialVersionUID = 699545056242003278L;
    @TableField("id")
    private String id;
    /**
     * 批次名称
     */
    @TableField("batch_name")
    private String batchName;
    /**
     * 利润中心组code
     */
    @TableField("profit_center_group_code")
    private String profitCenterGroupCode;
    /**
     * 盘点地市
     */
    @TableField("region_ids")
    private String regionIds;
    /**
     * 批次状态,0数据初始化,1盘点中,2归档
     */
    @TableField("batch_status")
    private String batchStatus;
    /**
     * 资产类别
     */
    @TableField("assets_type")
    private String assetsType;
    /**
     * ABC类
     */
    @TableField("assets_abc")
    private String assetsAbc;
    /**
     * 基准日期
     */
    @TableField("datum_date")
    private Date datumDate;
    /**
     * 开始时间
     */
    @TableField("start_date")
    private Date startDate;
    /**
     * 结束时间
     */
    @TableField("end_date")
    private Date endDate;
    /**
     * 资产总数
     */
    @TableField("assets_number")
    private Integer assetsNumber;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 备注
     */
    @TableField("remark")
    private Object remark;
    /**
     * 创建人 身份id
     */
    @TableField("create_by_ide")
    private String createByIde;
    @TableField("criterion_date")
    private Date criterionDate;
    /**
     * 批次状态
     */
    @TableField("batch_type")
    private String batchType;


}