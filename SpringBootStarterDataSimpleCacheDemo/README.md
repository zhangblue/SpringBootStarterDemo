# SpringBoot SimpleCache 
SpringBoot cache缓存机制，默认使用的Sample


## 笔记

### 1.主要模块引入
```xml
<dependencies>
    <!--引入mybatis模块-->
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>1.3.2</version>
    </dependency>

    <!--引入web模块-->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--引入postgres 连接-->
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
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

### 2.配置参数解读
```yaml
spring:
  datasource:
    # 数据库连接地址
    url: jdbc:postgresql://172.16.18.240:5432/zhangdi_test
    # 数据库连接驱动
    driver-class-name: org.postgresql.Driver
    # 数据库用户名
    username: dizhang

mybatis:
  configuration:
    # 开启驼峰命名法，否则属性dId与数据库中的d_id无法匹配
    map-underscore-to-camel-case: true

logging:
  level:
    # 设置com.zhangblue.sample.cache.mapper包下的日志打印级别为debug
    com.zhangblue.sample.cache.mapper: debug

```
### 3. 重点内容解读
- `@MapperScan(value = "com.zhangblue.sample.cache.mapper")` main函数添加。需要让mybatis扫描的mapper的包
- `@EnableCaching` main函数中添加。开启缓存注解功能，否则缓存不起作用。
- `@Mapper` 标记此类为数据连接类
- `@CacheConfig(cacheNames = {"emp"},key = "#id")` 标记在类上。缓存的公共配置
  - `cacheNames` : 缓存在哪个cache里
  - `key`：表示缓存的key。
  - `keyGenerator`：表示使用自定义的key生成策略函数的名称。需要自定义`KeyGenerator`类，并放入ioc容器。  
  - `condition`：表示调用方法前，只对满足某个条件的数据做缓存。
  - `unless`：表示方法返回值中，满足某个条件的数据不会被缓存。
- `@Cacheable(key = "#id")` 标记在方法上，表示查询缓存。当缓存中没有时会执行方法，并将方法的返回值缓存在cache中。缓存中如果已经存在，则直接获取缓存中的内容，不再执行方法。
- `@CachePut(key = "#result.id")` : 标记在方法上，更新缓存。方法肯定会被执行，执行后的结果更新cache。`key="#result.id"`表示使用返回值对象中的id字段作为缓存的key
- `@CacheEvict`：标记在方法上。清除缓存
 - `beforeInvocation`：表示是在调用方法前清除缓存，还是方法执行后才清除缓存。
 - `allEntries`：表示是否要将所有缓存都清除。
- `@Caching`：定义复杂的缓存机制。可以自定义组合缓存。 

### 4. 重点代码

**自定义cache key生成策略**

``` java
import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyCacheConfig {
  @Bean("myKeyGenerator") //key生成策略名称，在 keyGenerator 参数中使用
  public KeyGenerator getKeyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        return method.getName() + "[" + Arrays.asList(params).toString() + "]";
      }
    };
  }
}
```

**@Caching定义复杂的缓存规则**
``` java
@Caching(
    put = {
        @CachePut(cacheNames = {"emp"}, key = "#result.id"), //将返回值的id作为key放入cache中
        @CachePut(cacheNames = {"emp"}, key = "#result.email"),//将返回值的email作为key放入cache中
        @CachePut(cacheNames = {"emp"}, key = "#result.lastName")//将返回值的lastName作为key放入cache中
    }
)
public Employee getEmpByLastName(String lastName) {
  Employee employee = employeeMapper.getEmpByLastName(lastName);
  return employee;
}
```



