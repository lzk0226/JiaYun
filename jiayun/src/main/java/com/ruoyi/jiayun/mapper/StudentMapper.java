package com.ruoyi.jiayun.mapper;

import com.ruoyi.jiayun.domain.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper {

    /**
     * 插入新学生（带密码）
     * 假设数据库有password字段
     */
    @Insert("INSERT INTO student (user_id, name, gender, birthdate, phone, idcard, " +
            "license_type, address, avatar, register_date, password) " +
            "VALUES (#{student.userId}, #{student.name}, #{student.gender}, #{student.birthdate}, " +
            "#{student.phone}, #{student.idcard}, #{student.licenseType}, #{student.address}, " +
            "#{student.avatar}, #{student.registerDate}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "student.id")
    void insertWithPassword(@Param("student") Student student, @Param("password") String password);

    /**
     * 根据userId查询学生（不含密码）
     */
    @Select("SELECT id, user_id, name, gender, birthdate, phone, idcard, " +
            "license_type, address, avatar, register_date " +
            "FROM student WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "registerDate", column = "register_date")
    })
    Student findByUserId(@Param("userId") String userId);

    /**
     * 根据userId查询学生（含密码，仅用于登录验证）
     */
    @Select("SELECT id, user_id, name, gender, birthdate, phone, idcard, " +
            "license_type, address, avatar, register_date, password " +
            "FROM student WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "registerDate", column = "register_date")
    })
    Student findByUserIdWithPassword(@Param("userId") String userId);

    /**
     * 获取密码（用于登录验证）
     */
    @Select("SELECT password FROM student WHERE user_id = #{userId}")
    String getPasswordByUserId(@Param("userId") String userId);

    /**
     * 根据ID查询学生（不含密码）
     */
    @Select("SELECT id, user_id, name, gender, birthdate, phone, idcard, " +
            "license_type, address, avatar, register_date " +
            "FROM student WHERE id = #{id}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "registerDate", column = "register_date")
    })
    Student findById(@Param("id") Long id);

    /**
     * 更新学生信息（不更新密码）
     */
    @Update("UPDATE student SET name = #{name}, gender = #{gender}, " +
            "birthdate = #{birthdate}, phone = #{phone}, idcard = #{idcard}, " +
            "license_type = #{licenseType}, address = #{address}, avatar = #{avatar} " +
            "WHERE id = #{id}")
    void update(Student student);

    /**
     * 删除学生
     */
    @Delete("DELETE FROM student WHERE id = #{id}")
    void delete(@Param("id") Long id);
}