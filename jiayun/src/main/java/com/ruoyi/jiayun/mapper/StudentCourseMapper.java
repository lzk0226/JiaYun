package com.ruoyi.jiayun.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentCourseMapper {

    /**
     * 插入学员课程记录
     */
    @Insert("INSERT INTO student_course (student_id, course_id, coach_id, enroll_date, " +
            "total_lessons, progress, status) " +
            "SELECT #{studentId}, #{courseId}, #{coachId}, CURDATE(), " +
            "duration, 0.00, 'ongoing' " +
            "FROM course WHERE id = #{courseId}")
    int insertStudentCourse(@Param("studentId") Long studentId,
                            @Param("courseId") Long courseId,
                            @Param("coachId") Long coachId);

    /**
     * 检查学员是否已报名包含指定科目的课程
     * 返回true表示已报名，false表示未报名
     */
    @Select("<script>" +
            "SELECT COUNT(*) > 0 " +
            "FROM student_course sc " +
            "JOIN course c ON sc.course_id = c.id " +
            "WHERE sc.student_id = #{studentId} " +
            "AND sc.status = 'ongoing' " +
            "AND (" +
            "  (c.is_combined = 0 AND c.subject_id = #{subjectId}) " +
            "  OR " +
            "  (c.is_combined = 1 AND EXISTS (" +
            "    SELECT 1 FROM course_subject cs " +
            "    WHERE cs.course_id = c.id AND cs.subject_id = #{subjectId}" +
            "  ))" +
            ")" +
            "</script>")
    boolean hasEnrolledSubject(@Param("studentId") Long studentId,
                               @Param("subjectId") Integer subjectId);

    /**
     * 获取学员某个科目已报名的课程ID
     */
    @Select("<script>" +
            "SELECT sc.course_id " +
            "FROM student_course sc " +
            "JOIN course c ON sc.course_id = c.id " +
            "WHERE sc.student_id = #{studentId} " +
            "AND sc.status = 'ongoing' " +
            "AND (" +
            "  (c.is_combined = 0 AND c.subject_id = #{subjectId}) " +
            "  OR " +
            "  (c.is_combined = 1 AND EXISTS (" +
            "    SELECT 1 FROM course_subject cs " +
            "    WHERE cs.course_id = c.id AND cs.subject_id = #{subjectId}" +
            "  ))" +
            ") " +
            "LIMIT 1" +
            "</script>")
    Long getEnrolledCourseBySubject(@Param("studentId") Long studentId,
                                    @Param("subjectId") Integer subjectId);

}