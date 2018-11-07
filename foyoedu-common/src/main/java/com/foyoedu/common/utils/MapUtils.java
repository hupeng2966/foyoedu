package com.foyoedu.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapUtils<E> {

    private Class<?> clazz;

    public MapUtils(){
        super();
    }
    public MapUtils(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    public List<E> MapToList(List<Map<String, Object>> objs) throws Exception {
        List<E> list = new ArrayList<E>();
        for (Map<String, Object> obj:objs) {
            E o = MapToPojo(obj);
            list.add(o);
        }
        return list;
    }

    public E MapToPojo(Map<String, Object> obj) throws Exception {
        Field[] fs = clazz.getDeclaredFields();
        E o = (E)clazz.newInstance();
        for(Field field : fs){
            //获取属相名
            String attributeName = field.getName();
            if(obj.containsKey(attributeName)) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(o, obj.get(attributeName));
            }
        }
        return o;
    }

    public <E> Map<String, Object> PojoToMap(E obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }



//    public E MapToPojo(Map<String, Object> obj) throws Exception {
//        Field[] fs = clazz.getDeclaredFields();
//        E o = (E)clazz.newInstance();
//        for(Field field : fs){
//            //获取属相名
//            String attributeName = field.getName();
//            if(obj.containsKey(attributeName)) {
//                //将属性名的首字母变为大写，为执行set/get方法做准备
//                String methodName=attributeName.substring(0,1).toUpperCase()+attributeName.substring(1);
//
//                //获取o类当前属性的setXXX方法
//                Class fieldType = field.getType();
//                //String typeName = fieldType.getName();
//
//                Method m = clazz.getMethod("set"+methodName, fieldType);
//                //执行该set方法
//
//                String type = field.getGenericType().toString(); // 获取属性的类型
//                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
//                    String value = (String) obj.get(attributeName); // 调用getter方法获取属性值
//                    m.invoke(o, value);
//                }
//                if (type.equals("class java.lang.Integer")) {
//                    Integer value = (Integer) obj.get(attributeName); // 调用getter方法获取属性值
//                    m.invoke(o, value);
//                }
//                if (type.equals("class java.lang.Boolean")) {
//                    Boolean value = (Boolean) obj.get(attributeName); // 调用getter方法获取属性值
//                    m.invoke(o, value);
//                }
//                if (type.equals("class java.util.Date")) {
//                    Date value = (Date) obj.get(attributeName); // 调用getter方法获取属性值
//                    m.invoke(o, value);
//                }
//                if (type.equals("class java.lang.Long")) {
//                    Long value = (Long) obj.get(attributeName); // 调用getter方法获取属性值
//                    m.invoke(o, value);
//                }
//            }
//        }
//        return o;
//    }
}
