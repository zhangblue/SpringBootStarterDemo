package com.zhangblue.redis;

import com.zhangblue.redis.bean.User;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRedisApplication.class)
public class SpringBootRedisApplicationTest {

  @Autowired
  StringRedisTemplate stringRedisTemplate;

  @Autowired
  RedisTemplate redisTemplate;


  @Test
  public void testUser() {
    User user = new User();
    user.setId(1);
    user.setName("张三");
    user.setAge(10);
    redisTemplate.opsForValue().set("testUser",user );
  }

  @Test
  public void testString() {
    stringRedisTemplate.opsForValue().set("key1", "value1");
  }

  @Test
  public void testMap() {
    stringRedisTemplate.opsForValue().set("key1", "value1", 10, TimeUnit.SECONDS);
  }

  /**
   * 改变redis连接的库编号
   */
  @Test
  public void testSelectDatabase() {
    JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) stringRedisTemplate.getConnectionFactory();
    jedisConnectionFactory.setDatabase(2);
    stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
  }

}
