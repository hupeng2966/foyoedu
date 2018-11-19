package com.foyoedu.base.utils;

import com.foyoedu.base.service.BaseService;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.PageResult;
import com.foyoedu.common.utils.FoyoUtils;

import java.util.List;
import java.util.Map;

public class Utils {

    public static PageResult pageData(Map<String, String> map, BaseService obj, String filterCondition, String sortCondition)
            throws Throwable{
        Integer pageNo = 1;
        Integer pageSize = Integer.MAX_VALUE;
        if(map != null) {
            if (map.containsKey("pageNo") && map.containsKey("pageSize")) {
                pageNo = Integer.parseInt(map.get("pageNo"));
                pageSize = Integer.parseInt(map.get("pageSize"));
            }
        }
        Integer total = obj.totalCount(filterCondition);
        List<Dept> list = null;
        if(total > 0) {
            list = obj.list(pageNo, pageSize, filterCondition, sortCondition);
        }
        return FoyoUtils.pageResult(total, list);
    }
}
