package com.foyoedu.base.dao;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

public interface BaseDao {

    @Select("select * from ${table} where ${pk_id}=#{id}")
    public Map<String, Object> findById(@Param("id") Long id, @Param("table") String table, @Param("pk_id") String pk_id);

    @Select("select * from ${table} ${filterCondition} ${sortCondition} limit #{pageNo},#{pageSize}")
    public List<Map<String, Object>> findPage(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("table") String table, @Param("pk_id") String pk_id, @Param("filterCondition") String filterCondition, @Param("sortCondition") String sortCondition);

    @Select("select count(*) from ${table} ${filterCondition}")
    public Integer totalCount(@Param("table") String table, @Param("filterCondition") String filterCondition);

    @Delete("delete from ${table} where ${pk_id}=#{id}")
    public boolean deleteById(@Param("id") Long id, @Param("table") String table, @Param("pk_id") String pk_id);

    @Delete("delete from ${table} where ${filterCondition}")
    public boolean delete(@Param("table") String table, @Param("filterCondition") String filterCondition);

    @Insert("insert into ${table}(${insertColumns}) values(${values})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "new_id", before = false, resultType = Long.class)
    public boolean add(Map<String, Object> map);

    @Update("update ${table} set ${setColumns} where ${filterCondition}")
    public boolean update(@Param("table") String table, @Param("setColumns") String setColumns, @Param("filterCondition") String filterCondition);
}
