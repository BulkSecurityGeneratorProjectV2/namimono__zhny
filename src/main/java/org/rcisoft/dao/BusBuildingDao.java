package org.rcisoft.dao;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.rcisoft.entity.BusBuilding;
import org.rcisoft.entity.BusProject;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Create by 土豆儿
 * Time：2019/3/5 13:25
 **/
@Repository
public interface BusBuildingDao extends Mapper<BusBuilding> {

    /**
     * 获取建筑类型信息
     */
    @Select("SELECT * FROM bus_building;")
    @ResultType(BusProject.class)
    List<Map<String,Object>> queryBuildingInfo();
}