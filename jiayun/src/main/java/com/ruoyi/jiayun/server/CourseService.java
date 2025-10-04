package com.ruoyi.jiayun.server;

import com.ruoyi.jiayun.domain.Course;
import com.ruoyi.jiayun.domain.CourseDetail;

import java.util.List;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 6:54
 * @Author : SoakLightDust
 */
public interface CourseService {

    /**
     * 获取所有课程列表
     */
    List<Course> getAllCourses();

    /**
     * 根据筛选条件获取课程
     * @param subject 科目代码（subject2/subject3/all）
     * @param type 课程类型（standard/vip/intensive）
     * @param sort 排序方式（default/price-asc/price-desc/popular）
     */
    List<Course> getCoursesByFilters(String subject, String type, String sort);

    /**
     * 根据ID获取课程详情
     */
    CourseDetail getCourseDetail(Long id);

    /**
     * 报名课程
     */
    boolean enrollCourse(Long studentId, Long courseId, Long coachId);
}
