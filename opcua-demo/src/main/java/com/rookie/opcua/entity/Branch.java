package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
/**
 * @Author lsw
 * @Date 2018年7月6日 上午11:03:00
 * Company:广州磐信计算机科技有限公司
 * Description:	支局清单
 */
@Data
@ToString(callSuper = true)
@TableName("t_branch")
public class Branch{
	@TableField("id")
	private String id;
	

	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 编码 */
	@TableField("df_code")
	private String dfCode;
	
	/** 名称 */
	@TableField("df_name")
	private String dfName;

}
