package com.zhangblue.simple.cache.mapper;

import com.zhangblue.simple.cache.bean.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {


  @Select("SELECT * FROM employee WHERE id=#{id}")
  public Employee getEmployee(Integer id);

  @Update("UPDATE employee SET last_name=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} WHERE id=#{id}")
  public void updateEmp(Employee employee);

  @Delete("DELETE FROM employee WHERE id=#{id}")
  public void deleteEmyById(Integer id);

  @Insert("INSERT INTO employee(lastName,email,gender,d_id) VALUES(#{lastName},#{email},#{gender},#{dId})")
  public void insertEmployee(Employee employee);

  @Select("SELECT * FROM employee WHERE last_name=#{lastName}")
  Employee getEmpByLastName(String lastName);
}
