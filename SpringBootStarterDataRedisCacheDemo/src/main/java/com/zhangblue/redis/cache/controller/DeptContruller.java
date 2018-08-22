package com.zhangblue.redis.cache.controller;

import com.zhangblue.redis.cache.bean.Department;
import com.zhangblue.redis.cache.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptContruller {


  @Autowired
  DeptService deptService;

  @GetMapping("/dept/{id}")
  public Department getEmployee(@PathVariable("id") Integer id) {
    Department department = deptService.getDeptById(id);
    return department;
  }

  @GetMapping("/dept2/{id}")
  public Department getEmployee2(@PathVariable("id") Integer id) {
    Department department = deptService.getDeptById2(id);
    return department;
  }
}
