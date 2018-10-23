package com.foyoedu.base.utils;

import com.foyoedu.base.service.BaseService;
import com.foyoedu.common.pojo.Dept;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoyoUtils {

    public static Map<String, Object> pageData(MultiValueMap<String, String> mulMap, BaseService obj, String filterCondition, String sortCondition)
            throws Exception{
        Integer pageNo = 1;
        Integer pageSize = Integer.MAX_VALUE;
        if(mulMap.containsKey("pageNo") && mulMap.containsKey("pageSize")){
            pageNo = Integer.parseInt(mulMap.get("pageNo").get(0));
            pageSize = Integer.parseInt(mulMap.get("pageSize").get(0));
        }

        Map<String, Object> map = new HashMap<>();
        Integer total = obj.totalCount(filterCondition);
        map.put("total", total);
        if(total > 0) {
            List<Dept> list = obj.list(pageNo, pageSize, filterCondition, sortCondition);
            map.put("data", list);
        }

        return map;
    }
}
