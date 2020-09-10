package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.InventBatch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 批次表(InvenBach)表数据库访问层
 *
 * @author jxj
 * @since 2020-09-10 19:00:38
 */
public interface InventBachMapper extends BaseMapper<InventBatch> {

    /**
     * 查询批次
     *
     * @param year
     * @return
     */
    @Select("<script>" +
            "SELECT" +
            " * " +
            "FROM " +
            " t_invent_batch bat" +
            " WHERE " +
            " id IN ( SELECT p.batchId FROM t_project_batch p WHERE p.projectId IN ( " +
            "SELECT id FROM t_invent_project " +
            "<where>" +
            "   <if test=\"year != null and year != ''\">" +
            "      batch_name LIKE CONCAT(#{year},'%')" +
            "  </if>" +
            "</where>" +
            ") )" +
            "</script>")
    List<InventBatch> selectBatch(@Param("year") String year);

}