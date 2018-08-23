package com.zhangblue.sample.cache;

import com.zhangblue.simple.cache.SpringBootStarterDataSimpleCacheDemoApplication;
import com.zhangblue.simple.cache.bean.Employee;
import com.zhangblue.simple.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootStarterDataSimpleCacheDemoApplication.class)
public class SpringBootStarterDataSampleCacheDemoApplicationTest {

  @Autowired
  EmployeeMapper employeeMapper;

  @Test
  public void contextLoads(){
    Employee employee = employeeMapper.getEmployee(1);
    System.out.println(employee.getLastName());
  }

}
