package com.foyoedu.service.impl;

import com.foyoedu.pojo.Dept;
import com.foyoedu.service.DeptService;
import org.springframework.stereotype.Service;


@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {

    public DeptServiceImpl() {
        super("dept", "deptno", Dept.class);
    }

}
