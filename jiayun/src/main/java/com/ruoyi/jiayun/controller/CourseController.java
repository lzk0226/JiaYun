package com.ruoyi.jiayun.controller;

import com.ruoyi.jiayun.R.Result;
import com.ruoyi.jiayun.domain.Course;
import com.ruoyi.jiayun.domain.CourseDetail;
import com.ruoyi.jiayun.server.CourseService;
import com.ruoyi.jiayun.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 6:56
 * @Author : SoakLightDust
 */
@Slf4j
@RestController
@RequestMapping("/user/courses")
@Tag(name = "课程管理", description = "课程相关接口")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Resource
    private JwtUtils jwtUtils;

    @GetMapping
    @Operation(summary = "获取课程列表", description = "支持科目、类型、排序筛选")
    public Result<List<Course>> getCourses(
            @Parameter(description = "科目代码: subject2/subject3/all")
            @RequestParam(required = false) String subject,
            @Parameter(description = "课程类型: standard/vip/intensive")
            @RequestParam(required = false) String type,
            @Parameter(description = "排序方式: default/price-asc/price-desc/popular")
            @RequestParam(required = false, defaultValue = "default") String sort) {

        log.info("查询课程列表: subject={}, type={}, sort={}", subject, type, sort);

        List<Course> courses = courseService.getCoursesByFilters(subject, type, sort);
        return Result.success(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程详情")
    public Result<CourseDetail> getCourseDetail(
            @Parameter(description = "课程ID")
            @PathVariable Long id) {

        log.info("查询课程详情: id={}", id);

        CourseDetail detail = courseService.getCourseDetail(id);
        if (detail == null) {
            return Result.error("课程不存在");
        }
        return Result.success(detail);
    }

    @PostMapping("/enroll")
    @Operation(summary = "报名课程", description = "从token中获取学员ID,同一科目只能报名一个课程")
    public Result<String> enrollCourse(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "课程ID")
            @RequestParam Long courseId,
            @Parameter(description = "教练ID（可选）")
            @RequestParam(required = false) Long coachId) {

        try {
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 验证token
            if (!jwtUtils.validateToken(token)) {
                return Result.error("Token已过期，请重新登录");
            }

            // 从token获取学员ID
            Long studentId = jwtUtils.getUserIdFromToken(token);

            log.info("课程报名: studentId={}, courseId={}, coachId={}", studentId, courseId, coachId);

            // 调用service进行报名（包含科目冲突检查）
            String result = courseService.enrollCourse(studentId, courseId, coachId);

            if ("success".equals(result)) {
                return Result.success("报名成功");
            } else {
                return Result.error(result); // 返回具体错误信息
            }

        } catch (Exception e) {
            log.error("课程报名失败", e);
            return Result.error("报名失败：" + e.getMessage());
        }
    }
}