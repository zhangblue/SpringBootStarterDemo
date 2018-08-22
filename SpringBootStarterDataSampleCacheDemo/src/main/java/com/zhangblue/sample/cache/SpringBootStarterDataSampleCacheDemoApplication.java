package com.zhangblue.sample.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 搭建基本环境
 */
@MapperScan(value = "com.zhangblue.sample.cache.mapper") //表示需要让mybatis扫描的mapper的包
@SpringBootApplication
@EnableCaching //开启缓存注解
public class SpringBootStarterDataSampleCacheDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootStarterDataSampleCacheDemoApplication.class, args);
  }
}
