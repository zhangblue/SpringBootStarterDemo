package com.zhangblue.jpa.controller;

import com.zhangblue.jpa.entity.User;
import com.zhangblue.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/user/{id}")
  public User getUser(@PathVariable("id") Integer id) {
    return userRepository.findOne(id);
  }

  /**
   * http://localhost:8080/user?lastName=wangwu&email=sina.com
   */
  @GetMapping("/user")
  public User insertUser(User user) {
    return userRepository.save(user);
  }


}
