package com.ruoyi.jiayun.server;
import com.ruoyi.jiayun.domain.Course;
import com.ruoyi.jiayun.domain.CourseDetail;
import java.util.List;

public interface CourseService {

    /**
     * 获取所有课程
     */
    List<Course> getAllCourses();

    /**
     * 根据筛选条件获取课程
     */
    List<Course> getCoursesByFilters(String subject, String type, String sort);

    /**
     * 获取课程详情
     */
    CourseDetail getCourseDetail(Long id);

    /**
     * 报名课程（检查科目冲突）
     * @return "success" 或 具体错误信息
     */
    String enrollCourse(Long studentId, Long courseId, Long coachId);
}
