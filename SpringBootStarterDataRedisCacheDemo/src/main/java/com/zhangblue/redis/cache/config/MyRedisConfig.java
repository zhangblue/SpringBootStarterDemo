package com.zhangblue.redis.cache.config;

import com.zhangblue.redis.cache.bean.Department;
import com.zhangblue.redis.cache.bean.Employee;
import java.net.UnknownHostException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class MyRedisConfig {

  @Bean
  public RedisTemplate<Object, Employee> empRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
    //初始化一个json序列化器
    Jackson2JsonRedisSerializer<Employee> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Employee.class);

    RedisTemplate<Object, Employee> template = new RedisTemplate<>();
    template.setDefaultSerializer(jackson2JsonRedisSerializer);//设置redis默认初始化器
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

  @Bean
  public RedisTemplate<Object, Department> deptRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
    //初始化一个json序列化器
    Jackson2JsonRedisSerializer<Department> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Department.class);

    RedisTemplate<Object, Department> template = new RedisTemplate<>();
    template.setDefaultSerializer(jackson2JsonRedisSerializer);//设置redis默认初始化器
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

  /**
   * CacheManagerCustomizers 可以来定制缓存的一些规则
   */
  @Bean(name = "employeeCacheManager")
  public RedisCacheManager employeeCacheManager(RedisTemplate<Object, Employee> employeeRedisTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(employeeRedisTemplate);
    cacheManager.setUsePrefix(true);//使用前缀，会默认将CacheName作为key的前缀
    return cacheManager;
  }

  /**
   * CacheManagerCustomizers 可以来定制缓存的一些规则
   */
  @Bean(name = "deptCacheManager")
  public RedisCacheManager deptCacheManager(RedisTemplate<Object, Department> departmentRedisTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(departmentRedisTemplate);
    cacheManager.setUsePrefix(true);//使用前缀，会默认将CacheName作为key的前缀
    return cacheManager;
  }

  /**
   * 将系统默认的 CacheManager 设置为默认的 CacheManager
   */
  @Bean
  @Primary
  public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    cacheManager.setUsePrefix(true); //使用前缀，会默认将CacheName作为key的前缀
    return cacheManager;
  }


}
