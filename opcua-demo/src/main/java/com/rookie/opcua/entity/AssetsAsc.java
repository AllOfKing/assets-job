package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
/**
 * 
 * @Author lsw
 * @Date 2018年11月26日 下午8:57:19
 * Company:广州磐信计算机科技有限公司
 * Description:	资产归属
 */
@Data
@ToString(callSuper = true)
@TableName("t_assets_asc")
public class AssetsAsc{

	@TableField("ID")
	private String id;
	
	/** 评审小组 1 - 4 */
	@TableField("ORD4X")
	private String ord4x;

	/** 评审小组:短描述 */
	@TableField("ORDTX")
	private String ordtx;
	
	/** 表id */
	@TableField("TID")
	private String tid;
}
