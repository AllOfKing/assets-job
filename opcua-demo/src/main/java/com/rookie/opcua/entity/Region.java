package com.rookie.opcua.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * 地市
 * @author Gu
 *
 */
@Data
@TableName("sys_region")
public class Region implements Serializable, Cloneable {

	@TableField("ID")
	private String id = "";

	/** 父ID */
	@TableField("PARENT_ID")
	private String parentId;

	/** 名称 */
	@TableField("REGION_NAME")
	private String regionName = "";

	/** 是否有效 */
	@TableField("IS_VALID")
	private String isValid = "1";

	/** 备注 */
	@TableField("REMARKS")
	private String remarks;

	/** 创建人 */
	@TableField("CREATE_BY")
	protected String createBy = "admin";

	/** 创建时间  */
	@TableField("CREATE_TIME")
	protected Timestamp createTime;

	/** 最后更新人 */
	@TableField("LAST_UPDATE_BY")
	protected String lastUpdateBy = "admin";

	/** 最后更新时间 */
	@TableField("LAST_UPDATE_TIME")
	protected Timestamp lastUpdateTime;
}
