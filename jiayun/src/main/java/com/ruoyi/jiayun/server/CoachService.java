package com.ruoyi.jiayun.server;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.CoachDetail;
import com.ruoyi.jiayun.domain.StudentCourse;

import java.util.List;

public interface CoachService {

    /**
     * 获取所有教练
     */
    List<Coach> getAllCoaches();

    /**
     * 根据筛选条件获取教练
     */
    List<Coach> getCoachesByFilters(String subject, String level, String sort);

    /**
     * 获取教练详情
     */
    CoachDetail getCoachDetail(Long id);

    /**
     * 获取学员可选择教练的课程列表
     * 返回没有教练或可以更换教练的课程
     */
    List<StudentCourse> getCoursesForCoachSelection(Long studentId);

    /**
     * 为课程选择教练
     * @param studentId 学员ID
     * @param courseId 课程ID
     * @param coachId 教练ID
     * @return 操作结果消息
     */
    String selectCoachForCourse(Long studentId, Long courseId, Long coachId);
}