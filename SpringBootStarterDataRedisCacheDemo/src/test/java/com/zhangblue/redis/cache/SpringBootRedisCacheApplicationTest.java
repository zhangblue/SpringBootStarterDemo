package com.zhangblue.redis.cache;

import com.zhangblue.redis.cache.bean.Employee;
import com.zhangblue.redis.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRedisCacheApplication.class)
public class SpringBootRedisCacheApplicationTest {

  @Autowired
  EmployeeMapper employeeMapper;

  @Autowired
  RedisTemplate<Object, Employee> empRedisTemplate;


  /**
   * 使用自定义的redis序列化器将对象放入redis
   */
  @Test
  public void test02() {
    Employee employee = employeeMapper.getEmployee(1);
    System.out.println(employee.toString());
    empRedisTemplate.opsForValue().set("emp-01", employee);
  }

  /**
   * 使用自定义的redis序列化器将对象从redis中取出
   */
  @Test
  public void test03() {
    Employee emp01 = empRedisTemplate.opsForValue().get("emp-01");

    System.out.println(emp01.toString());
  }


}
