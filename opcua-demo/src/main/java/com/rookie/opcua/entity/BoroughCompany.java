package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
/**
 * @Author lsw
 * @Date 2018年7月6日 上午11:03:00
 * Company:广州磐信计算机科技有限公司
 * Description:	区县分公司
 */
@Data
@ToString(callSuper = true)
@TableName("t_borough_company")
public class BoroughCompany{

	@TableField("id")
	private String id;
	
	/** 区县分公司 */
	@TableField("df_code")
	private String dfCode;
	
	/** 区县分公司描述 */
	@TableField("df_name")
	private String dfName;
	
	/** 表id */
	@TableField("TID")
	private String tid;

}
