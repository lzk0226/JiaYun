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
                case "subject1":
                    subjectId = 1;
                    break;
                case "subject2":
                    subjectId = 2;
                    break;
                case "subject3":
                    subjectId = 3;
                    break;
                case "subject4":
                    subjectId = 4;
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
    public String enrollCourse(Long studentId, Long courseId, Long coachId) {
        try {
            log.info("开始报名检查: studentId={}, courseId={}", studentId, courseId);

            // 1. 获取要报名的课程信息
            CourseDetail course = courseMapper.selectCourseDetailById(courseId);
            if (course == null) {
                return "课程不存在";
            }

            // 2. 获取课程包含的科目ID列表
            List<Integer> courseSubjects = courseMapper.getCourseSubjects(courseId);
            if (courseSubjects == null || courseSubjects.isEmpty()) {
                return "课程科目信息错误";
            }

            log.info("课程包含科目: {}", courseSubjects);

            // 3. 检查学员是否已报名包含相同科目的课程
            for (Integer subjectId : courseSubjects) {
                boolean hasEnrolled = studentCourseMapper.hasEnrolledSubject(studentId, subjectId);
                if (hasEnrolled) {
                    // 获取科目名称用于提示
                    String subjectName = getSubjectName(subjectId);
                    return "您已报名了包含" + subjectName + "的课程，同一科目只能报名一个课程";
                }
            }

            // 4. 没有冲突，可以报名
            int result = studentCourseMapper.insertStudentCourse(studentId, courseId, coachId);
            if (result > 0) {
                // 更新课程报名人数
                courseMapper.updateStudentsCount(courseId);
                log.info("课程报名成功: studentId={}, courseId={}", studentId, courseId);
                return "success";
            }

            return "报名失败，请重试";

        } catch (Exception e) {
            log.error("课程报名失败: studentId={}, courseId={}", studentId, courseId, e);
            throw new RuntimeException("课程报名失败", e);
        }
    }

    /**
     * 根据科目ID获取科目名称
     */
    private String getSubjectName(Integer subjectId) {
        switch (subjectId) {
            case 1:
                return "科目一";
            case 2:
                return "科目二";
            case 3:
                return "科目三";
            case 4:
                return "科目四";
            default:
                return "未知科目";
        }
    }
}