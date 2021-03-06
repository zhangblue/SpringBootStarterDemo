package com.zhangblue.elasticsearch.jest;

import com.zhangblue.elasticsearch.SpringBootStarterDataElasticsearchDemo;
import com.zhangblue.elasticsearch.bean.Article;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Index.Builder;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootStarterDataElasticsearchDemo.class)
public class SpringBootStarterDataElasticsearchDemoTest {

  //-----以下使用jest方式进行es操作-------
  @Autowired
  JestClient jestClient;

  //1.给ES中索引一个文档
  @Test
  public void contextLoads() {

    Article article = new Article();
    article.setId(1);
    article.setTitle("好消息");
    article.setAuthor("张三");
    article.setContent("Hello World");

    //构建一个索引功能
    Index index = new Builder(article).index("zhangd_test").type("news").build();
    try {
      jestClient.execute(index);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //测试搜索
  @Test
  public void search() {
    String json = "{\n"
        + "\"query\":{\n"
        + "\"match\":{\n"
        + "\"content\":\"hello\"\n"
        + "}\n"
        + "}\n"
        + "}";
    Search search = new Search.Builder(json).addIndex("zhangd_test").addType("news").build();
    try {
      SearchResult searchResult = jestClient.execute(search);
      System.out.println(searchResult.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
