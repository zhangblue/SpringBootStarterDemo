package com.zhangblue.redis.cache.service;

import com.zhangblue.redis.cache.bean.Department;
import com.zhangblue.redis.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"dept"}, cacheManager = "deptCacheManager")
public class DeptService {

  @Autowired
  DepartmentMapper departmentMapper;


  /**
   * 使用标签的形式做缓存。使用此种方式，这个方法不会被重复调用。
   */
  @Cacheable(key = "#id")
  public Department getDeptById(Integer id) {
    System.out.println("查询" + id + "号部门");
    Department department = departmentMapper.getDepartmenById(id);
    return department;
  }


}
