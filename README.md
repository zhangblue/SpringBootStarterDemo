# 说明
本工程为学习SpringBoot所有starters模块的代码案例。

# 构建方式
- 所有模块都使用maven方式构建。
- 所有模块都使用的SpringBoot版本`2.0.4.RELEASE`进行构建(当前发布的稳定版本)。

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.4.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>
```

# 包含内容
- SpringBootJpa操作 `SpringBootStarterDataJpaDemo`
- SpringBootRedis操作 `SpringBootStarterDataRedisDemo`
- SpringBootSimpleCache操作 `SpringBootStarterDataSimpleCacheDemo`
- SpringBootRedisCache操作 `SpringBootStarterDataRedisCacheDemo`

其他模块内容正在完善中...