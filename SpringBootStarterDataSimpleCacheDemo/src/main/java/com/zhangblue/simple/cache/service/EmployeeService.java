package com.zhangblue.simple.cache.service;

import com.zhangblue.simple.cache.bean.Employee;
import com.zhangblue.simple.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"emp"}) //缓存的公共配置
public class EmployeeService {

  @Autowired
  EmployeeMapper employeeMapper;


  @Cacheable(key = "#id")
  public Employee getEmp(Integer id) {
    System.out.println("查询" + id + "号员工");
    Employee employee = employeeMapper.getEmployee(id);
    return employee;
  }

  @CachePut(key = "#result.id")
  public Employee updateEmp(Employee employee) {
    System.out.println("update emp " + employee);
    employeeMapper.updateEmp(employee);
    return employee;
  }

  @CacheEvict(cacheNames = {"emp"}, key = "#id", beforeInvocation = false)
  public String deleteEmp(Integer id) {
    System.out.println("删除" + id + "号员工");
    //employeeMapper.deleteEmyById(id);
    return "success";
  }


  /**
   * 定义复杂的缓存规则
   */
  @Caching(
      put = {
          @CachePut(cacheNames = {"emp"}, key = "#result.id"),
          @CachePut(cacheNames = {"emp"}, key = "#result.email"),
          @CachePut(cacheNames = {"emp"}, key = "#result.lastName")
      }
  )
  public Employee getEmpByLastName(String lastName) {
    Employee employee = employeeMapper.getEmpByLastName(lastName);
    return employee;
  }
}
