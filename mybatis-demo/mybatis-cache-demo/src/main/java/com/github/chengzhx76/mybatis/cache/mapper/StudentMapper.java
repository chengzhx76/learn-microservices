package com.github.chengzhx76.mybatis.cache.mapper;

import com.github.chengzhx76.mybatis.cache.entity.StudentEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/5 17:33
 * @Version 3.0
 */
public interface StudentMapper {

    StudentEntity getStudentById(int id);

    int addStudent(StudentEntity student);

    int updateStudentName(@Param("name") String name, @Param("id") int id);

    StudentEntity getStudentByIdWithClassInfo(int id);
}
