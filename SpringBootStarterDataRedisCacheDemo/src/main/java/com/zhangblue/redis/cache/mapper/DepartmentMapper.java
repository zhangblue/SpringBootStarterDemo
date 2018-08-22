package com.zhangblue.redis.cache.mapper;

import com.zhangblue.redis.cache.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DepartmentMapper {

  @Select("SELECT * FROM department WHERE id=#{id}")
  public Department getDepartmenById(Integer id);

}
