package com.github.chengzhx76.mybatis.cache.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/5 17:31
 * @Version 3.0
 */
public interface ClassMapper {

    int updateClassName(@Param("name") String className, @Param("id") int id);
}
