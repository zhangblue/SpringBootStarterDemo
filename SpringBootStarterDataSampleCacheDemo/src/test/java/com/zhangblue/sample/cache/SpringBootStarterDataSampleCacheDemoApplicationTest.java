package com.zhangblue.sample.cache;

import com.zhangblue.sample.cache.bean.Employee;
import com.zhangblue.sample.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootStarterDataSampleCacheDemoApplication.class)
public class SpringBootStarterDataSampleCacheDemoApplicationTest {

  @Autowired
  EmployeeMapper employeeMapper;

  @Test
  public void contextLoads(){
    Employee employee = employeeMapper.getEmployee(1);
    System.out.println(employee.getLastName());
  }

}
