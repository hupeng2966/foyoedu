package com.foyoedu.base.service.impl;

import com.foyoedu.base.dao.BaseDao;
import com.foyoedu.base.service.BaseService;
import com.foyoedu.common.utils.MapUtils;
import com.foyoedu.common.utils.SqlUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class BaseServiceImpl<E> implements BaseService<E> {

    private String TBALENAME;
    private String PK_ID;
    private Class<?> clazz;

    @Autowired
    private BaseDao dao;

    public BaseServiceImpl(String TBALENAME, String PK_ID, Class<?> clazz) {
        this.TBALENAME = TBALENAME;
        this.PK_ID = PK_ID;
        this.clazz = clazz;
    }

    public E getById(Long id) throws Exception {
        Map<String, Object> map = dao.findById(id, TBALENAME, PK_ID);
        MapUtils<E> mapUtils = new MapUtils<E>(clazz);
        return mapUtils.MapToPojo(map);
    }

    public boolean deleteById(Long id) {
        return dao.deleteById(id, TBALENAME, PK_ID);
    }

    public boolean delete(String filterCondition) throws Throwable {
        if (SqlUtils.sqlValidate(filterCondition)) {
            throw new Exception("SQL参数中含有非法字符");
        }
        return dao.delete(TBALENAME, filterCondition);
    }

    public List<E> list(String filterCondition, String sortCondition) throws Throwable {
        return list(1,Integer.MAX_VALUE,filterCondition,sortCondition);
    }

    public List<E> list(Integer pageNo, Integer pageSize, String filterCondition, String sortCondition) throws Throwable {
        if (SqlUtils.sqlValidate(filterCondition) || SqlUtils.sqlValidate(sortCondition)) {
            throw new Exception("SQL参数中含有非法字符");
        }

        List<Map<String, Object>> maps = dao.findPage((pageNo - 1) * pageSize, pageSize, TBALENAME, PK_ID, filterCondition, sortCondition);
        MapUtils<E> mapUtils = new MapUtils<E>(clazz);
        return mapUtils.MapToList(maps);
    }

    public boolean update(String setColumns, String filterCondition) throws Throwable {
        if (SqlUtils.sqlValidate(filterCondition)) {
            throw new Exception("SQL参数中含有非法字符");
        }
        return dao.update(TBALENAME, setColumns, filterCondition);
    }

    public boolean add(E obj, String inserColumns, String values) throws Throwable {
        if (SqlUtils.sqlValidate(inserColumns) || SqlUtils.sqlValidate(values)) {
            throw new Exception("SQL参数中含有非法字符");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("insertColumns", inserColumns);
        map.put("values", values);
        map.put("table", TBALENAME);
        boolean b = dao.add(map);
        Field field = obj.getClass().getDeclaredField(PK_ID);
        field.setAccessible(true);
        field.set(obj, map.get("new_id"));
        return b;
    }

    public Integer totalCount(String filterConditon) throws Throwable {
        if (SqlUtils.sqlValidate(filterConditon)) {
            throw new Exception("SQL参数中含有非法字符");
        }
        return dao.totalCount(TBALENAME, filterConditon);
    }
}
