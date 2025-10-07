package com.ruoyi.jiayun.controller;
import com.ruoyi.jiayun.R.Result;
import com.ruoyi.jiayun.domain.Student;
import com.ruoyi.jiayun.server.StudentService;
import com.ruoyi.jiayun.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 学生用户控制器 - 处理登录注册
 */
@RestController
@RequestMapping("/user/student")
@CrossOrigin
public class StudentController {

    @Resource
    private StudentService studentService;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> registerData) {
        try {
            String name = registerData.get("name");
            String userId = registerData.get("userId");
            String phone = registerData.get("phone");
            String password = registerData.get("password");

            // 校验必填字段
            if (name == null || name.trim().isEmpty()) {
                return Result.error("姓名不能为空");
            }
            if (userId == null || userId.trim().isEmpty()) {
                return Result.error("邮箱不能为空");
            }
            if (password == null || password.trim().isEmpty()) {
                return Result.error("密码不能为空");
            }

            // 检查邮箱是否已存在
            Student existStudent = studentService.findByUserId(userId);
            if (existStudent != null) {
                return Result.error("该邮箱已被注册");
            }

            // 创建学生对象
            Student student = new Student();
            student.setName(name);
            student.setUserId(userId);
            student.setPhone(phone); // 手机号正常存储，不加密

            // 注册新用户（密码单独处理）
            studentService.register(student, password);
            return Result.success("注册成功");
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        try {
            String userId = loginData.get("email");
            String password = loginData.get("password");

            // 校验参数
            if (userId == null || userId.trim().isEmpty()) {
                return Result.error("邮箱不能为空");
            }
            if (password == null || password.trim().isEmpty()) {
                return Result.error("密码不能为空");
            }

            // 验证用户
            Student student = studentService.login(userId, password);
            if (student == null) {
                return Result.error("邮箱或密码错误");
            }

            // 生成token
            String token = jwtUtils.generateToken(student.getId(), student.getName());
            String refreshToken = jwtUtils.generateRefreshToken(student.getId(), student.getName());

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("refreshToken", refreshToken);
            data.put("user", student);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    /**
     * 刷新token
     */
    @PostMapping("/refresh")
    public Result<Map<String, String>> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            // 移除Bearer前缀
            if (refreshToken.startsWith("Bearer ")) {
                refreshToken = refreshToken.substring(7);
            }

            // 验证refresh token
            if (!jwtUtils.validateToken(refreshToken)) {
                return Result.error("Token已过期，请重新登录");
            }

            // 获取用户信息
            Long userId = jwtUtils.getUserIdFromToken(refreshToken);
            String userName = jwtUtils.getUserNameFromToken(refreshToken);

            // 生成新token
            String newToken = jwtUtils.generateToken(userId, userName);
            String newRefreshToken = jwtUtils.generateRefreshToken(userId, userName);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("token", newToken);
            tokens.put("refreshToken", newRefreshToken);

            return Result.success(tokens);
        } catch (Exception e) {
            return Result.error("刷新Token失败：" + e.getMessage());
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token) {
        try {
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 将token加入黑名单
            jwtUtils.invalidateToken(token);

            return Result.success("退出成功");
        } catch (Exception e) {
            return Result.error("退出失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<Student> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 验证token
            if (!jwtUtils.validateToken(token)) {
                return Result.error("Token已过期，请重新登录");
            }

            // 获取用户ID
            Long userId = jwtUtils.getUserIdFromToken(token);
            Student student = studentService.findById(userId);

            if (student == null) {
                return Result.error("用户不存在");
            }

            return Result.success(student);
        } catch (Exception e) {
            return Result.error("获取用户信息失败：" + e.getMessage());
        }
    }
}