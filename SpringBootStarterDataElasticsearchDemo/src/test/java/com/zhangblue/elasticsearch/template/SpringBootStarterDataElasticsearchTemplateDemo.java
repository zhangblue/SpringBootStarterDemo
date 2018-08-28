package com.zhangblue.elasticsearch.template;

import com.zhangblue.elasticsearch.SpringBootStarterDataElasticsearchDemo;
import com.zhangblue.elasticsearch.bean.Book;
import com.zhangblue.elasticsearch.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootStarterDataElasticsearchDemo.class)
public class SpringBootStarterDataElasticsearchTemplateDemo {

  @Autowired
  BookRepository bookRepository;
  @Autowired
  ElasticsearchTemplate elasticsearchTemplate;

  @Test
  public void test02(){
    Book book = new Book();
    book.setId(1);
    book.setBookName("西游记");
    book.setAuthor("吴承恩");
    bookRepository.index(book);
  }

  @Test
  public void test03(){
    List<Book> listB =bookRepository.findByBookNameLike("西游记");
    System.out.println(listB.get(0).toString());
  }

  @Test
  public void test04(){
    UpdateQuery updateQuery = new UpdateQuery();
    List<UpdateQuery> updateQueryList = new ArrayList<>();
    updateQueryList.add(updateQuery);
    elasticsearchTemplate.bulkUpdate(updateQueryList);

  }


}
