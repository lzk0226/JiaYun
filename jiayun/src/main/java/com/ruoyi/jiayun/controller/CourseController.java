package com.ruoyi.jiayun.controller;

import com.ruoyi.jiayun.R.Result;
import com.ruoyi.jiayun.domain.Course;
import com.ruoyi.jiayun.domain.CourseDetail;
import com.ruoyi.jiayun.server.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class CourseController {

    @Autowired
    private CourseService courseService;

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
    @Operation(summary = "报名课程")
    public Result<String> enrollCourse(
            @Parameter(description = "学员ID")
            @RequestParam Long studentId,
            @Parameter(description = "课程ID")
            @RequestParam Long courseId,
            @Parameter(description = "教练ID（可选）")
            @RequestParam(required = false) Long coachId) {

        log.info("课程报名: studentId={}, courseId={}, coachId={}", studentId, courseId, coachId);

        boolean success = courseService.enrollCourse(studentId, courseId, coachId);
        if (success) {
            return Result.success("报名成功");
        }
        return Result.error("报名失败");
    }
}
