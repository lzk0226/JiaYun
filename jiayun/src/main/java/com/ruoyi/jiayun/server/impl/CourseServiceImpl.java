package com.ruoyi.jiayun.server.impl;

import com.ruoyi.jiayun.domain.Course;
import com.ruoyi.jiayun.domain.CourseDetail;
import com.ruoyi.jiayun.mapper.CourseMapper;
import com.ruoyi.jiayun.mapper.StudentCourseMapper;
import com.ruoyi.jiayun.server.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 6:54
 * @Author : SoakLightDust
 */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public List<Course> getAllCourses() {
        return courseMapper.selectAllCourses();
    }

    @Override
    public List<Course> getCoursesByFilters(String subject, String type, String sort) {
        Integer subjectId = null;
        Boolean isCombined = null;

        // 处理科目筛选
        if (subject != null && !subject.isEmpty()) {
            switch (subject) {
                case "subject2":
                    subjectId = 2;
                    break;
                case "subject3":
                    subjectId = 3;
                    break;
                case "all":
                    isCombined = true;
                    break;
            }
        }

        // 查询课程
        List<Course> courses = courseMapper.selectCoursesWithFilters(subjectId, isCombined, type);

        // 处理排序
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "price-asc":
                    courses = courses.stream()
                            .sorted(Comparator.comparing(Course::getPrice))
                            .collect(Collectors.toList());
                    break;
                case "price-desc":
                    courses = courses.stream()
                            .sorted(Comparator.comparing(Course::getPrice).reversed())
                            .collect(Collectors.toList());
                    break;
                case "popular":
                    courses = courses.stream()
                            .sorted(Comparator.comparing(Course::getPopular).reversed())
                            .collect(Collectors.toList());
                    break;
                // default 保持数据库默认排序（按 popular DESC）
            }
        }

        return courses;
    }

    @Override
    public CourseDetail getCourseDetail(Long id) {
        return courseMapper.selectCourseDetailById(id);
    }

    @Override
    @Transactional
    public boolean enrollCourse(Long studentId, Long courseId, Long coachId) {
        try {
            // 插入学员课程记录
            int result = studentCourseMapper.insertStudentCourse(studentId, courseId, coachId);
            if (result > 0) {
                // 更新课程报名人数
                courseMapper.updateStudentsCount(courseId);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("课程报名失败: studentId={}, courseId={}", studentId, courseId, e);
            throw new RuntimeException("课程报名失败", e);
        }
    }
}