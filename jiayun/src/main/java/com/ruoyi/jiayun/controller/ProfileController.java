package com.ruoyi.jiayun.controller;

import com.ruoyi.jiayun.R.Result;
import com.ruoyi.jiayun.domain.Student;
import com.ruoyi.jiayun.server.ProfileService;
import com.ruoyi.jiayun.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/5下午 1:14
 * @Author : SoakLightDust
 */
@Slf4j
@RestController
@RequestMapping("/user/profile")
@Tag(name = "个人中心", description = "学员个人信息相关接口")
@CrossOrigin
public class ProfileController {

    @Resource
    private ProfileService profileService;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * 获取个人中心完整数据
     */
    @GetMapping
    @Operation(summary = "获取个人中心数据",
            description = "包含个人信息、学习进度、课程、考试等完整数据")
    public Result<Map<String, Object>> getProfile(
            @RequestHeader("Authorization") String token) {
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
            Long studentId = jwtUtils.getUserIdFromToken(token);

            log.info("获取个人中心数据: studentId={}", studentId);

            Map<String, Object> profileData = profileService.getStudentProfile(studentId);
            return Result.success(profileData);
        } catch (Exception e) {
            log.error("获取个人中心数据失败", e);
            return Result.error("获取数据失败：" + e.getMessage());
        }
    }

    /**
     * 更新个人信息
     */
    @PutMapping
    @Operation(summary = "更新个人信息")
    public Result<String> updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody Student student) {
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
            Long studentId = jwtUtils.getUserIdFromToken(token);
            student.setId(studentId);

            log.info("更新个人信息: studentId={}, student={}", studentId, student);

            boolean success = profileService.updateStudentInfo(student);
            if (success) {
                return Result.success("更新成功");
            }
            return Result.error("更新失败");
        } catch (Exception e) {
            log.error("更新个人信息失败", e);
            return Result.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 单独更新头像
     */
    @PutMapping("/avatar")
    @Operation(summary = "更新头像")
    public Result<String> updateAvatar(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> avatarData) {
        try {
            log.info("收到更新头像请求");

            // 移除Bearer前缀
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            log.info("处理后的token: {}", token != null ? token.substring(0, Math.min(20, token.length())) + "..." : "null");

            // 验证token
            if (!jwtUtils.validateToken(token)) {
                log.warn("Token验证失败");
                return Result.error("Token已过期，请重新登录");
            }

            // 获取用户ID
            Long studentId = jwtUtils.getUserIdFromToken(token);
            log.info("从token获取到studentId: {}", studentId);

            // 获取头像数据
            String avatar = avatarData.get("avatar");
            if (avatar == null || avatar.isEmpty()) {
                log.warn("头像数据为空");
                return Result.error("头像数据不能为空");
            }

            log.info("头像数据长度: {}", avatar.length());
            log.info("头像数据前缀: {}", avatar.substring(0, Math.min(50, avatar.length())));

            // 调用服务更新头像
            boolean success = profileService.updateAvatar(studentId, avatar);

            if (success) {
                log.info("头像更新成功: studentId={}", studentId);
                return Result.success("头像更新成功");
            } else {
                log.warn("头像更新失败: studentId={}", studentId);
                return Result.error("头像更新失败");
            }
        } catch (Exception e) {
            log.error("更新头像异常", e);
            return Result.error("更新失败：" + e.getMessage());
        }
    }
}