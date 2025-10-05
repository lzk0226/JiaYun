package com.ruoyi.jiayun.server.impl;

import com.ruoyi.jiayun.domain.Student;
import com.ruoyi.jiayun.mapper.StudentMapper;
import com.ruoyi.jiayun.server.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/4下午 11:41
 * @Author : SoakLightDust
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    /**
     * 用户注册
     * @param student 学生信息
     * @param password 密码（明文）
     */
    @Override
    public void register(Student student, String password) {
        // 设置注册时间
        student.setRegisterDate(LocalDate.now());

        // 密码加密后存储
        String encryptedPassword = encryptPassword(password);

        // 插入数据库（密码加密存储，手机号正常存储）
        studentMapper.insertWithPassword(student, encryptedPassword);
    }


    /**
     * 用户登录
     */
    @Override
    public Student login(String userId, String password) {
        // 根据userId查询用户（包含密码）
        Student student = studentMapper.findByUserIdWithPassword(userId);

        if (student == null) {
            return null;
        }

        // 验证密码（从数据库查询的密码字段）
        String encryptedPassword = encryptPassword(password);
        String storedPassword = studentMapper.getPasswordByUserId(userId);

        if (storedPassword == null || !encryptedPassword.equals(storedPassword)) {
            return null;
        }

        // 返回不含密码的用户信息
        return studentMapper.findByUserId(userId);
    }

    /**
     * 根据userId查询用户（不含密码）
     */
    @Override
    public Student findByUserId(String userId) {
        return studentMapper.findByUserId(userId);
    }

    /**
     * 根据ID查询用户（不含密码）
     */
    @Override
    public Student findById(Long id) {
        return studentMapper.findById(id);
    }

    /**
     * 密码加密
     */
    private String encryptPassword(String password) {
        // 使用MD5加密，实际项目建议使用BCrypt
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
