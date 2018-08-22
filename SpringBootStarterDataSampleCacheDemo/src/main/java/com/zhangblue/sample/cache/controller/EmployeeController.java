package com.zhangblue.sample.cache.controller;

import com.zhangblue.sample.cache.bean.Employee;
import com.zhangblue.sample.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @GetMapping("/emp/{id}")
  public Employee getEmployee(@PathVariable("id") Integer id) {
    Employee emp = employeeService.getEmp(id);
    return emp;
  }

  @GetMapping("/emp")
  public Employee updateEmployee(Employee employee) {
    System.out.println(employee.getLastName());
    return employeeService.updateEmp(employee);
  }


  @GetMapping("/delete/emp/{id}")
  public String deleteEmployee(@PathVariable("id") Integer id) {
    return employeeService.deleteEmp(id);
  }

  @GetMapping("/emp/lastName/{lastName}")
  public Employee getEmpByLastName(@PathVariable("lastName") String lastName) {
    return employeeService.getEmpByLastName(lastName);
  }
}
