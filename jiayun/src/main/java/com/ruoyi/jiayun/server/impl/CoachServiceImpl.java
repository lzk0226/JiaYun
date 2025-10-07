package com.ruoyi.jiayun.server.impl;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.CoachDetail;
import com.ruoyi.jiayun.domain.StudentCourse;
import com.ruoyi.jiayun.mapper.CoachMapper;
import com.ruoyi.jiayun.mapper.StudentCourseMapper;
import com.ruoyi.jiayun.server.CoachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachMapper coachMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public List<Coach> getAllCoaches() {
        return coachMapper.selectAllCoaches();
    }

    @Override
    public List<Coach> getCoachesByFilters(String subject, String level, String sort) {
        Integer subjectId = null;

        // 处理科目筛选
        if (subject != null && !subject.isEmpty() && !"all".equals(subject)) {
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
            }
        }

        // 查询教练
        List<Coach> coaches = coachMapper.selectCoachesWithFilters(subjectId, level);

        // 处理排序
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "rating":
                    coaches = coaches.stream()
                            .sorted(Comparator.comparing(Coach::getRating).reversed())
                            .collect(Collectors.toList());
                    break;
                case "experience":
                    coaches = coaches.stream()
                            .sorted(Comparator.comparing(Coach::getExperience).reversed())
                            .collect(Collectors.toList());
                    break;
                // default 保持数据库默认排序(按 rating DESC)
            }
        }

        return coaches;
    }

    @Override
    public CoachDetail getCoachDetail(Long id) {
        return coachMapper.selectCoachDetailById(id);
    }

    @Override
    public List<StudentCourse> getCoursesForCoachSelection(Long studentId) {
        log.info("获取学员可选择教练的课程列表: studentId={}", studentId);

        // 获取学员所有正在进行的课程
        List<StudentCourse> courses = studentCourseMapper.getStudentOngoingCourses(studentId);

        log.info("学员共有 {} 门正在进行的课程", courses.size());

        return courses;
    }

    @Override
    @Transactional
    public String selectCoachForCourse(Long studentId, Long courseId, Long coachId) {
        try {
            log.info("开始选择教练检查: studentId={}, courseId={}, coachId={}",
                    studentId, courseId, coachId);

            // 1. 检查课程是否存在且属于该学员
            List<StudentCourse> courses = studentCourseMapper.getStudentOngoingCourses(studentId);
            boolean courseExists = courses.stream()
                    .anyMatch(sc -> sc.getCourseId().equals(courseId));

            if (!courseExists) {
                return "该课程不存在或不属于您";
            }

            // 2. 检查该课程是否已有教练
            Long existingCoachId = studentCourseMapper.getCoachByCourse(studentId, courseId);

            if (existingCoachId != null) {
                // 如果选择的是同一个教练，提示无需更换
                if (existingCoachId.equals(coachId)) {
                    return "您已选择该教练，无需重复选择";
                }
                // 如果要更换教练，提示联系工作人员
                return "该课程已有教练，若需更换请联系工作人员";
            }

            // 3. 检查教练是否存在
            CoachDetail coach = coachMapper.selectCoachDetailById(coachId);
            if (coach == null) {
                return "教练不存在";
            }

            // 4. 检查教练是否有资格教授该课程的科目
            boolean isQualified = studentCourseMapper.isCoachQualifiedForCourse(coachId, courseId);
            if (!isQualified) {
                return "该教练不教授此课程相关科目";
            }

            // 5. 为课程分配教练
            int result = studentCourseMapper.updateCourseCoach(studentId, courseId, coachId);

            if (result > 0) {
                log.info("教练选择成功: studentId={}, courseId={}, coachId={}",
                        studentId, courseId, coachId);
                return "success";
            }

            return "选择教练失败，请重试";

        } catch (Exception e) {
            log.error("选择教练失败: studentId={}, courseId={}, coachId={}",
                    studentId, courseId, coachId, e);
            throw new RuntimeException("选择教练失败", e);
        }
    }
}

