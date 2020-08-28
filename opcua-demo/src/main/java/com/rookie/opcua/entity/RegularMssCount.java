package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("t_regular_mss_count")
public class RegularMssCount{
    @TableField("ID")
    private String id;

    /** 部门id */
    @TableField("dept_id")
    private String deptId;

    /** 部门名称 */
    @TableField("dept_name")
    private String deptName;

    /** 父部门id */
    @TableField("p_id")
    private String pId = "0";

    /** 区域编号 */
    @TableField("region_id")
    private String regionId;

    /** 是否存在子部门 */
    @TableField("is_leaf")
    private Integer isLeaf = 0;

    /** 需触发卡片数量 */
    @TableField("card_num")
    private Integer cardNum = 0;

    /** 更新未触发 */
    @TableField("gxwcf")
    private Integer gxwcf = 0;

    /** 更新已触发 */
    @TableField("gxycf")
    private Integer gxycf = 0;

    /** 调拨未触发 */
    @TableField("dbwcf")
    private Integer dbwcf = 0;

    /** 调拨已触发 */
    @TableField("dbycf")
    private Integer dbycf = 0;

    /** 盘亏未触发 */
    @TableField("pkwcf")
    private Integer pkwcf = 0;

    /** 盘亏以触发 */
    @TableField("pkycf")
    private Integer pkycf = 0;

    /** 盘盈未触发 */
    @TableField("pywcf")
    private Integer pywcf = 0;

    /** 盘盈以触发 */
    @TableField("pyycf")
    private Integer pyycf = 0;

    /** 重分类未触发*/
    @TableField("cflwcf")
    private Integer cflwcf = 0;

    /** 重分类以触发 */
    @TableField("cflycf")
    private Integer cflycf = 0;
}
