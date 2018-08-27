# SpringBootStarterDataElasticsearchDemo

SpringBoot整合Elasticsearch
## 说明
SpringBoot版本与elasticsearch版本对应关系参见[https://github.com/spring-projects/spring-data-elasticsearch](https://github.com/spring-projects/spring-data-elasticsearch)

## 笔记
### 连接方式
SpringBoot默认支持两种技术来和ES交互

- Jest (默认不生效)
 - 需要导入jest的工具包(io.searchbox.client.JestClient)
- SpringData ElasticSearch
 - client节点信息 clusternodes,clusterName
 - ElasticsearchTemplate操作es
 - 编写一个ElasticsearchRepository的子接口来操作ES，类似于JPA

### 模块引入
#### 1. 使用JestClient方式
**pom文件引入**
```xml
<!-- 使用jest模块-->
<dependency>
  <groupId>io.searchbox</groupId>
  <artifactId>jest</artifactId>
  <version>6.3.0</version>
</dependency>
```
**配置文件引入**

```yaml
# 使用 jest 配置文件
spring:
  elasticsearch:
    jest:
      uris: http://172.16.18.41:9200
```


2. 使用ElasticsearchRepositor
此方法类似与JPA，可自定义查询方法，自定义查询方法需要满足一下规则
自定义查询函数名称规范：[https://docs.spring.io/spring-data/elasticsearch/docs/3.0.9.RELEASE/reference/html/#elasticsearch.query-methods](https://docs.spring.io/spring-data/elasticsearch/docs/3.0.9.RELEASE/reference/html/#elasticsearch.query-methods)

3. 使用ElasticsearchTemplate方式 

