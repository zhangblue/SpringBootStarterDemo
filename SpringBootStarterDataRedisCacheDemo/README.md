# SpringBootStarterDataRedisCacheDemo
**使用Redis作为cache缓存数据**
>此部分承接SpringBootStarterDataSimpleCache部分的内容。其中对于cache缓存的使用方法在SpringBootStarterDataSimpleCache中已经说明，本节只介绍使用redis作为缓存如何设置

## 笔记
### 1. 模块引入
``` xml
<dependencies>

  <!--使用mybatis-->
  <dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
  </dependency>

  <!--引入postgres 连接-->
  <dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
  </dependency>

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

```yaml
spring:
  redis:
    host: 172.16.18.240
    port: 6379
    database: 1
  datasource:
    url: jdbc:postgresql://172.16.18.240:5432/zhangdi_test
    driver-class-name: org.postgresql.Driver
    username: dizhang

mybatis:
  configuration:
    # 开启驼峰命名法，否则属性dId与数据库中的d_id无法匹配
    map-underscore-to-camel-case: true

logging:
  level:
    com.zhangblue.redis.cache.mapper: debug
debug: true
```

### 3. 重点内容
> 1. 当引入了`spring-boot-starter-data-redis`模块后，SpringBoot自动会将`SimpleCacheConfiguration`替换为`RedisCacheConfiguration`作为cache缓存方式
> 2. 当使用redis缓存自定义对象时，自定义对象需要实现序列化接口，同时会使用普通的java序列化器的方式将对象写入redis
> 3. 如果不想使用默认的java序列化器，则可以编写自己的序列化器。

**例：使用`Jackson2JsonRedisSerializer`序列化器**

当有**多个**不同的自定义类需要需要进行自定义序列化时，需要初始化每一个类型的`RedisTemplate`与缓存管理器`RedisCacheManager`。并且需要制定一个默认使用的缓存管理器，否则在进行反序列化时，会报错。

```java
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
  @Bean
  public RedisCacheManager employeeCacheManager(RedisTemplate<Object, Employee> employeeRedisTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(employeeRedisTemplate);
    cacheManager.setUsePrefix(true);//使用前缀，会默认将CacheName作为key的前缀
    return cacheManager;
  }

  /**
   * CacheManagerCustomizers 可以来定制缓存的一些规则
   */
  @Bean
  public RedisCacheManager deptCacheManager(RedisTemplate<Object, Department> deptTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(deptTemplate);
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
```

**需要指定使用哪一个缓存管理器cacheManager**

1. 使用`employeeCacheManager`作为缓存管理器进行缓存`Employee`类型数据

```java

@Service
@CacheConfig(cacheNames = {"emp"}, cacheManager = "employeeCacheManager") //缓存的公共配置
public class EmployeeService {

  @Autowired
  EmployeeMapper employeeMapper;


  @Cacheable(key = "#id")
  public Employee getEmp(Integer id) {
    System.out.println("查询" + id + "号员工");
    Employee employee = employeeMapper.getEmployee(id);
    return employee;
  }

  @CachePut(key = "#result.id")
  public Employee updateEmp(Employee employee) {
    System.out.println("update emp " + employee);
    employeeMapper.updateEmp(employee);
    return employee;
  }

  @CacheEvict(cacheNames = {"emp"}, key = "#id", beforeInvocation = false)
  public String deleteEmp(Integer id) {
    System.out.println("删除" + id + "号员工");
    //employeeMapper.deleteEmyById(id);
    return "success";
  }


  /**
   * 定义复杂的缓存规则
   */
  @Caching(
      put = {
          @CachePut(cacheNames = {"emp"}, key = "#result.id"),
          @CachePut(cacheNames = {"emp"}, key = "#result.email"),
          @CachePut(cacheNames = {"emp"}, key = "#result.lastName")
      }
  )
  public Employee getEmpByLastName(String lastName) {
    Employee employee = employeeMapper.getEmpByLastName(lastName);
    return employee;
  }
}
```

2. 使用`deptCacheManager`作为缓存管理器缓存`Department`类型数据

```java
@Service
@CacheConfig(cacheNames = {"dept"}, cacheManager = "deptCacheManager")
public class DeptService {

  @Autowired
  DepartmentMapper departmentMapper;

  @Qualifier(value = "deptCacheManager")//明确规定使用名为 deptCacheManager CacheManager
  @Autowired
  RedisCacheManager departmentCacheManager;


  /**
   * 使用标签的形式做缓存。使用此种方式，这个方法不会被重复调用。
   */
  @Cacheable(key = "#id")
  public Department getDeptById(Integer id) {
    System.out.println("查询" + id + "号部门");
    Department department = departmentMapper.getDepartmenById(id);
    return department;
  }

  /**
   * 使用缓存管理器得到缓存，进行api调用
   * 在代码中进行缓存。这样只是对内容进行了缓存，方法还是会被重复调用
   */
  public Department getDeptById2(Integer id) {
    System.out.println("查询" + id + "号部门");
    Department department = departmentMapper.getDepartmenById(id);
    departmentCacheManager.getCache("dept").put(department.getId(), department);
    return department;
  }
}
```


