package com.foyoedu.service.impl;

import com.foyoedu.dao.BaseDao;
import com.foyoedu.service.BaseService;
import com.foyoedu.utils.TypeConvert;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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

    public E get(Long id) throws Exception {
        Map<String, Object> map = dao.findById(id, TBALENAME, PK_ID);
        TypeConvert<E> typeConvert = new TypeConvert<E>(clazz);
        return typeConvert.MapToPojo(map);
    }

    public boolean deleteById(Long id) {
        return dao.deleteById(id, TBALENAME, PK_ID);
    }

    public boolean delete(String filterCondition) {
        return dao.delete(TBALENAME, filterCondition);
    }

    public List<E> list(Integer pageNo, Integer pageSize, String filterCondition, String sortCondition) throws Exception {
        List<Map<String, Object>> maps = dao.findPage((pageNo-1)*pageSize, pageSize, TBALENAME, PK_ID, filterCondition, sortCondition);
        TypeConvert<E> typeConvert = new TypeConvert<E>(clazz);
        return typeConvert.MapToPojo(maps);
    }

    public boolean update(String setColumns, String filterCondition) {
        return dao.update(TBALENAME, setColumns, filterCondition);
    }

    public boolean add(E obj, String inserColumns, String values) throws Exception{
        TypeConvert<E> typeConvert = new TypeConvert<E>();
        Map<String, Object> map = typeConvert.PojoToMap(obj);
        map.put("insertColumns", inserColumns);
        map.put("values", values);
        map.put("pk_id", PK_ID);
        map.put("table", TBALENAME);
        boolean b = dao.add(map);
        Field field = obj.getClass().getDeclaredField(PK_ID);
        field.setAccessible(true);
        field.set(obj, map.get("new_id"));
        return b;
    }

    public Integer totalCount(String filterConditon) {
        return dao.totalCount(TBALENAME, filterConditon);
    }
}
