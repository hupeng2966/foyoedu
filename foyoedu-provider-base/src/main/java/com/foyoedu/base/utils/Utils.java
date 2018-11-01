package com.foyoedu.base.utils;

import com.foyoedu.base.service.BaseService;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.PageResult;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static PageResult pageData(MultiValueMap<String, String> mulMap, BaseService obj, String filterCondition, String sortCondition)
            throws Throwable{
        Integer pageNo = 1;
        Integer pageSize = Integer.MAX_VALUE;
        if(mulMap.containsKey("pageNo") && mulMap.containsKey("pageSize")){
            pageNo = Integer.parseInt(mulMap.get("pageNo").get(0));
            pageSize = Integer.parseInt(mulMap.get("pageSize").get(0));
        }

        Integer total = obj.totalCount(filterCondition);
        List<Dept> list = null;
        if(total > 0) {
            list = obj.list(pageNo, pageSize, filterCondition, sortCondition);
        }
        return FoyoUtils.pageResult(total, list);
    }
}
