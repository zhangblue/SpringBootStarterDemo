package com.zhangblue.redis.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(value = "com.zhangblue.redis.cache.mapper")
@SpringBootApplication
@EnableCaching
public class SpringBootRedisCacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootRedisCacheApplication.class, args);
  }
}
