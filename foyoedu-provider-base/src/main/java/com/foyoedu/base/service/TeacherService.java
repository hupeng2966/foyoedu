package com.foyoedu.base.service;

import com.foyoedu.common.pojo.Teacher;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherService extends BaseService<Teacher> {
    public int addTeacherData(MultipartFile file) throws Throwable;

}
