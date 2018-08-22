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

  @Qualifier(value = "deptCacheManager")//明确规定使用名为 deptCacheManager CacheManager
  @Autowired
  RedisCacheManager departmentCacheManager;


  /**
   * 使用标签的形式做缓存。使用此种方式，这个方法不会被重复调用。
   */
  @Cacheable(key = "#id")
  public Department getDeptById(Integer id) {
    System.out.println("查询" + id + "号部门");
    Department department = departmentMapper.getDepartmenById(id);
    return department;
  }

  /**
   * 使用缓存管理器得到缓存，进行api调用
   * 在代码中进行缓存。这样只是对内容进行了缓存，方法还是会被重复调用
   */
  public Department getDeptById2(Integer id) {
    System.out.println("查询" + id + "号部门");
    Department department = departmentMapper.getDepartmenById(id);
    departmentCacheManager.getCache("dept").put(department.getId(), department);
    return department;
  }
}
