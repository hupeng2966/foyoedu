package com.foyoedu.base.service;

import com.foyoedu.common.pojo.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService extends BaseService<Teacher> {
    public int addTeacherData(MultipartFile file) throws Throwable;

    public List<Teacher> findTeacherData() throws Throwable;

    public void addUserChoiceCourse(Integer courseId, String orderNumber);
}
