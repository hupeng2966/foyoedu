package com.foyoedu.base.service.impl;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.base.service.DeptService;
import org.springframework.stereotype.Service;


@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {

    public DeptServiceImpl() {
        super("dept", "deptno", Dept.class);
    }

}
