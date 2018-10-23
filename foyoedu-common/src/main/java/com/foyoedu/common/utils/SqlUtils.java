package com.foyoedu.common.utils;

public class SqlUtils {
    //效验
    public static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "exec|execute|insert|select|delete|update|drop|master|truncate|" +
                "net user|xp_cmdshell|create|column_name|information_schema.columns|table_schema";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }
}
