package com.foyoedu.base.dao;

import com.foyoedu.common.pojo.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeacherDao {
    @Select("select * from teacher where loginId=#{loginId}")
    public Teacher findByLoginId(@Param("loginId") String loginId);

    /**
     * replace into 替换数据库记录，需要表中有主键或者unique索引，如果数据库已存在的数据，会先删除该数据然后新增。不存在的数据效果和insert into 一样。
     * insert ignore 需要表中有主键或者unique索引，如果数据库中存在相同的数据，则忽略当前数据。不存在的数据效果和insert into 一样。
     * on duplicate key update 需要表中有主键或者unique索引，使用该语法可在插入记录的时候先判断记录是否存在，如果不存在则插入，否则更新
     */
    @Insert({
            "<script>",
            "insert into teacher(userName, loginId, pwd) values ",
            "<foreach collection='teacherList' item='item' index='index' separator=','>",
                "(#{item.userName}, #{item.loginId}, #{item.pwd})",
            "</foreach>",
            "ON DUPLICATE KEY UPDATE userName=values(userName),pwd=values(pwd)",
            "</script>"
    })
    public int addTeacherList(@Param(value="teacherList") List<Teacher> list);

    @Insert("insert into userCourse(loginId,courseId) values(#{loginId},#{courseId})")
    public boolean addUserChoiceCourse(@Param(value = "loginId") String loginId, @Param(value = "courseId") Integer courseId);

    @Select("select courseNum from course where courseId=#{courseId}")
    public int findCourseNumById(@Param(value = "courseId") Integer courseId);

    @Update("update course set courseNum=0 where courseId=#{courseId}")
    public boolean updateCourse(@Param("courseId") Integer courseId);
}
