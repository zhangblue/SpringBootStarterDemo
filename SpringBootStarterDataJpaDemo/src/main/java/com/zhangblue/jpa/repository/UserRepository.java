package com.zhangblue.jpa.repository;

import com.zhangblue.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//继承 JpaRepository 来完成对数据库的操作
public interface UserRepository extends JpaRepository<User, Integer> {

}
