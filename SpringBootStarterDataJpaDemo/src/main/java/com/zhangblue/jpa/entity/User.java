package com.zhangblue.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//使用jpa注解配置映射关系
@Entity //告诉jpa这是一个实体类(和数据表的映射类)
@Table(name = "tbl_user") // 标识与哪个数据库表对应，如果默认使用小写类名

public class User {

  @Id //这是一个主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) //自增主键
  private Integer id;
  @Column(name = "last_name",length = 50) //这是和数据不傲对应的一列
  private String lastName;
  @Column //省略默认列明就是属性名
  private String email;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
