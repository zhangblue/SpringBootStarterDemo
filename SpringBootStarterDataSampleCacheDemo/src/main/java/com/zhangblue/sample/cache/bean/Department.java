package com.zhangblue.sample.cache.bean;

public class Department {
  private Integer id;
  private String departmentNames;

  public Department() {
  }

  public Department(Integer id, String departmentNames) {
    this.id = id;
    this.departmentNames = departmentNames;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDepartmentNames() {
    return departmentNames;
  }

  public void setDepartmentNames(String departmentNames) {
    this.departmentNames = departmentNames;
  }

  @Override
  public String toString() {
    return "Department{" +
        "id=" + id +
        ", departmentNames='" + departmentNames + '\'' +
        '}';
  }
}
