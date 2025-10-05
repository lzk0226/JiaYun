package com.ruoyi.jiayun.server;

import com.ruoyi.jiayun.domain.Student;

/**
 * 学生服务接口
 */
public interface StudentService {


    /**
     * 用户注册
     */
    void register(Student student,  String password);

    /**
     * 用户登录
     */
    Student login(String userId, String password);

    /**
     * 根据 userId 查询用户
     */
    Student findByUserId(String userId);

    /**
     * 根据 ID 查询用户
     */
    Student findById(Long id);
}
