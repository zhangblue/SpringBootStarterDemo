# SpringBootStarterRedis 

SpringBoot Redis的操作

## 笔记
### 1. 主要模块引入

```xml
<dependencies>
  <!--引入web模块-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <!--引入redis模块-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>

  <!--引入测试模块-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <!--导入配置文件处理器，配置文件进行绑定就会有提示-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 2. 配置参数解读

``` yaml
spring:
  redis:
    # 设置redis地址
    host: 172.16.36.134
    # 设置redis端口
    port: 6379
    # 设置默认连接的数据库
    database: 1
```

### 3. 重点内容
**SpringBoot 提供两种Redis的封装格式，一种为操作字符串类型的k-v；另一种为操作对象形式的k-v**

StringBoot源码部分
```java
/**
 * Standard Redis configuration.
 */
public class RedisAutoConfiguration {

...

	@Configuration
	protected static class RedisConfiguration {
		@Bean
		@ConditionalOnMissingBean(name = "redisTemplate")
		public RedisTemplate<Object, Object> redisTemplate(
				RedisConnectionFactory redisConnectionFactory)
						throws UnknownHostException {
			RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
			template.setConnectionFactory(redisConnectionFactory);
			return template;
		}
		@Bean
		@ConditionalOnMissingBean(StringRedisTemplate.class)
		public StringRedisTemplate stringRedisTemplate(
				RedisConnectionFactory redisConnectionFactory)
						throws UnknownHostException {
			StringRedisTemplate template = new StringRedisTemplate();
			template.setConnectionFactory(redisConnectionFactory);
			return template;
		}
	}
}
```

`StringRedisTemplate`与`RedisTemplate`都提供`opsForxxx`函数来操作不同类型数据。

**如果要使用StringRedisTemplate将自定义对象类型放入redis中，则自定义对象需要实现序列化接口**

```java
package com.zhangblue.redis.bean;

import java.io.Serializable;

public class User implements Serializable{

  private Integer id;
  private String name;
  private int age;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}

```

```java
@Test
public void testUser() {
  User user = new User();
  user.setId(1);
  user.setName("张三");
  user.setAge(10);
  redisTemplate.opsForValue().set("testUser",user );
}
```

**动态改变所使用的redis的库编号**
```java
/**
 * 改变redis连接的库编号
 */
@Test
public void testSelectDatabase() {
  JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) stringRedisTemplate.getConnectionFactory();
  jedisConnectionFactory.setDatabase(2); //将使用的redis库编号改为2
  stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
}
```



