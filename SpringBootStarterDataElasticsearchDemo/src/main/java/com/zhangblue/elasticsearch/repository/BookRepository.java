package com.zhangblue.elasticsearch.repository;

import com.zhangblue.elasticsearch.bean.Book;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

  public List<Book> findByBookNameLike(String bookName);


}
