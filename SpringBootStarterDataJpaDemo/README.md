# SpringBoot Jpa 操作简介

jpa用在与数据库的操作

## 笔记

### 1. 主要模块引入
```xml
    <!--引入jpa 模块-->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!--引入postgres 连接-->
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
    </dependency>
```

### 2. 配置参数解读
```yaml
spring:
  datasource:
    # 数据库连接地址
    url: jdbc:postgresql://172.16.36.134:5432/zhangdi_test
    # 数据库驱动
    driver-class-name: org.postgresql.Driver
    # 数据库用户
    username: bangcle_pg
  jpa:
    hibernate:
      # 更新或者创建数据表
      ddl-auto: update
    # 控制台显示sql
    show-sql: true

```

### 3. 重点内容解读
1. 继承`JpaRepository`接口来得到与数据库的所有操作
2. `@Entity`标签用于告诉jpa这是一个实体类(和数据表的映射类)
3. @Table(name = "xxx") // 标识与哪个数据库表对应，如果默认使用小写类名
4. @Id //这是一个主键
5. @GeneratedValue(strategy = GenerationType.IDENTITY) //自增主键
6. @Column(name = "xxxx",length = 50) //标记这个属性对应表的那一列

