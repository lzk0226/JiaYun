package com.ruoyi.jiayun.server.impl;
import com.ruoyi.jiayun.domain.*;
import com.ruoyi.jiayun.mapper.ProfileMapper;
import com.ruoyi.jiayun.mapper.StudentMapper;
import com.ruoyi.jiayun.server.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {

    @Resource
    private ProfileMapper profileMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Map<String, Object> getStudentProfile(Long studentId) {
        log.info("开始获取学员信息: studentId={}", studentId);

        Map<String, Object> result = new HashMap<>();

        // 1. 基本信息
        Student student = studentMapper.findById(studentId);
        if (student == null) {
            log.warn("未找到学员信息: studentId={}", studentId);
            throw new RuntimeException("学员信息不存在");
        }
        result.put("userData", student);

        // 2. 统计数据
        Statistics statistics = profileMapper.getStudentStatistics(studentId);
        if (statistics != null) {
            List<Map<String, Object>> stats = new ArrayList<>();

            stats.add(createStat("fa-clock", statistics.getCompletedLessons(), "已完成课时", "blue"));
            String passedSubjects = statistics.getPassedSubjects() + "/" + statistics.getTotalSubjects();
            stats.add(createStat("fa-check-circle", passedSubjects, "科目通过", "green"));
            stats.add(createStat("fa-calendar-check", statistics.getReservationCount(), "预约次数", "purple"));
            String overallProgress = statistics.getOverallProgress() + "%";
            stats.add(createStat("fa-trophy", overallProgress, "整体进度", "orange"));

            result.put("stats", stats);
        } else {
            result.put("stats", new ArrayList<>());
        }

        // 3. 学习进度时间线
        List<LearningProgress> progressList = profileMapper.getStudentLearningProgress(studentId);
        List<Exam> examList = profileMapper.getStudentExams(studentId);
        List<Map<String, Object>> timeline = buildTimeline(progressList, examList);
        result.put("timeline", timeline);

        // 4. 课程列表
        List<StudentCourse> courses = profileMapper.getStudentCourses(studentId);
        List<Map<String, Object>> coursesData = buildCoursesData(courses);
        result.put("courses", coursesData);

        // 5. 考试安排
        List<Map<String, Object>> examsData = buildExamsData(examList);
        result.put("exams", examsData);

        log.info("成功获取学员完整信息: studentId={}", studentId);
        return result;
    }

    /**
     * 构建统计卡片数据
     */
    private Map<String, Object> createStat(String icon, Object value, String label, String color) {
        Map<String, Object> stat = new HashMap<>();
        stat.put("icon", icon);
        stat.put("value", value);
        stat.put("label", label);
        stat.put("color", color);
        return stat;
    }

    /**
     * 构建学习时间线
     */
    private List<Map<String, Object>> buildTimeline(List<LearningProgress> progressList, List<Exam> examList) {
        List<Map<String, Object>> timeline = new ArrayList<>();

        Map<Integer, LearningProgress> progressMap = new HashMap<>();
        for (LearningProgress p : progressList) {
            progressMap.put(p.getSubjectId(), p);
        }

        Map<Integer, Exam> examMap = new HashMap<>();
        for (Exam e : examList) {
            examMap.put(e.getSubjectId(), e);
        }

        for (int i = 1; i <= 4; i++) {
            Map<String, Object> item = new HashMap<>();
            LearningProgress progress = progressMap.get(i);
            Exam exam = examMap.get(i);

            if (progress != null && progress.getSubject() != null) {
                item.put("title", progress.getSubject().getSubjectName());

                String status = "未开始";
                if (exam != null) {
                    if ("passed".equals(exam.getStatus())) {
                        status = "已通过";
                    } else if ("scheduled".equals(exam.getStatus())) {
                        status = "进行中";
                    }
                } else if ("in_progress".equals(progress.getStatus())) {
                    status = "进行中";
                } else if ("completed".equals(progress.getStatus())) {
                    status = "已完成";
                }
                item.put("status", status);

                if (exam != null) {
                    item.put("time", exam.getExamTime());
                    if (exam.getScore() != null) {
                        item.put("score", exam.getScore());
                    }
                    if (exam.getExamPlace() != null) {
                        item.put("location", exam.getExamPlace());
                    }
                }

                if (progress.getCompletedLessons() != null) {
                    item.put("completedLessons", progress.getCompletedLessons());
                }
                if (progress.getProgressPercent() != null) {
                    item.put("progress", progress.getProgressPercent() + "%");
                }

                timeline.add(item);
            }
        }

        return timeline;
    }

    /**
     * 构建课程数据
     */
    private List<Map<String, Object>> buildCoursesData(List<StudentCourse> courses) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (StudentCourse sc : courses) {
            Map<String, Object> courseData = new HashMap<>();
            courseData.put("title", sc.getCourse() != null ? sc.getCourse().getName() : "未知课程");
            courseData.put("coach", sc.getCoach() != null ? sc.getCoach().getName() : "待分配");
            courseData.put("lessons", sc.getTotalLessons());
            courseData.put("start", sc.getStartDate());

            int progress = 0;
            if (sc.getProgress() != null) {
                progress = sc.getProgress().intValue();
            } else if (sc.getTotalLessons() > 0) {
                progress = (sc.getCompletedLessons() * 100) / sc.getTotalLessons();
            }
            courseData.put("progress", progress);

            result.add(courseData);
        }

        return result;
    }

    /**
     * 构建考试数据
     */
    private List<Map<String, Object>> buildExamsData(List<Exam> exams) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Exam exam : exams) {
            Map<String, Object> examData = new HashMap<>();
            examData.put("title", exam.getTitle());
            examData.put("place", exam.getExamPlace());
            examData.put("time", exam.getExamTime());

            String status = "未知";
            if ("passed".equals(exam.getStatus())) {
                status = "已通过";
            } else if ("failed".equals(exam.getStatus())) {
                status = "未通过";
            } else if ("scheduled".equals(exam.getStatus())) {
                status = "待考试";
            } else if ("cancelled".equals(exam.getStatus())) {
                status = "已取消";
            }
            examData.put("status", status);

            result.add(examData);
        }

        return result;
    }

    @Override
    @Transactional
    public boolean updateStudentInfo(Student student) {
        try {
            log.info("开始更新学员信息: studentId={}", student.getId());

            if (student.getId() == null) {
                log.error("学员ID不能为空");
                return false;
            }

            int rows = profileMapper.updateStudentInfo(student);

            log.info("更新学员信息结果: studentId={}, rows={}", student.getId(), rows);

            return rows > 0;
        } catch (Exception e) {
            log.error("更新学员信息失败: studentId={}", student.getId(), e);
            throw new RuntimeException("更新学员信息失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAvatar(Long studentId, String avatar) {
        try {
            log.info("Service层开始更新头像: studentId={}", studentId);

            if (studentId == null) {
                log.error("学员ID不能为空");
                throw new IllegalArgumentException("学员ID不能为空");
            }

            if (avatar == null || avatar.isEmpty()) {
                log.error("头像数据不能为空");
                throw new IllegalArgumentException("头像数据不能为空");
            }

            // 验证学员是否存在
            Student student = studentMapper.findById(studentId);
            if (student == null) {
                log.error("学员不存在: studentId={}", studentId);
                throw new RuntimeException("学员不存在");
            }

            log.info("准备更新数据库: studentId={}, avatar长度={}", studentId, avatar.length());

            // 执行更新
            int rows = profileMapper.updateAvatar(studentId, avatar);

            log.info("数据库更新结果: studentId={}, rows={}", studentId, rows);

            if (rows <= 0) {
                log.error("数据库更新失败，影响行数为0");
                throw new RuntimeException("数据库更新失败");
            }

            // 验证更新是否成功
            Student updatedStudent = studentMapper.findById(studentId);
            if (updatedStudent == null || updatedStudent.getAvatar() == null) {
                log.error("更新验证失败，头像未保存");
                throw new RuntimeException("头像更新验证失败");
            }

            log.info("头像更新成功: studentId={}", studentId);
            return true;

        } catch (IllegalArgumentException e) {
            log.error("参数错误: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("更新头像失败: studentId={}", studentId, e);
            throw new RuntimeException("更新头像失败: " + e.getMessage(), e);
        }
    }
}