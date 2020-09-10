package com.rookie.opcua.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * (Projecbatch)实体类
 *
 * @author jxj
 * @since 2020-09-10 18:50:10
 */
@Data
@TableName("t_project_batch")
public class ProjectBatch implements Serializable {
    private static final long serialVersionUID = 171865833042427101L;
    /**
     * id
     */
    @TableField("id")
    private String id;
    /**
     * 项目id
     */
    @TableField("projectId")
    private String projectid;
    /**
     * 批次id
     */
    @TableField("batchId")
    private String batchid;
    /**
     * 创建人 身份id
     */
    @TableField("createByIde")
    private String createbyide;
    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createtime;


}