package com.ruoyi.jiayun.mapper;

import com.ruoyi.jiayun.domain.StudentCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * 获取学员所有正在进行的课程
     */
    @Select("SELECT sc.id, sc.student_id, sc.course_id, sc.coach_id, " +
            "sc.enroll_date, sc.start_date, sc.completed_lessons, " +
            "sc.total_lessons, sc.progress, sc.status " +
            "FROM student_course sc " +
            "WHERE sc.student_id = #{studentId} AND sc.status = 'ongoing'")
    @Results({
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "coachId", column = "coach_id"),
            @Result(property = "enrollDate", column = "enroll_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "completedLessons", column = "completed_lessons"),
            @Result(property = "totalLessons", column = "total_lessons"),
            @Result(property = "courseDetail", column = "course_id",
                    one = @One(select = "com.ruoyi.jiayun.mapper.CourseMapper.selectCourseDetailById"))
    })
    List<StudentCourse> getStudentOngoingCourses(@Param("studentId") Long studentId);

    /**
     * 检查课程是否已有教练
     */
    @Select("SELECT coach_id FROM student_course " +
            "WHERE student_id = #{studentId} AND course_id = #{courseId} " +
            "AND status = 'ongoing'")
    Long getCoachByCourse(@Param("studentId") Long studentId,
                          @Param("courseId") Long courseId);

    /**
     * 更新课程的教练
     */
    @Update("UPDATE student_course SET coach_id = #{coachId} " +
            "WHERE student_id = #{studentId} AND course_id = #{courseId} " +
            "AND status = 'ongoing'")
    int updateCourseCoach(@Param("studentId") Long studentId,
                          @Param("courseId") Long courseId,
                          @Param("coachId") Long coachId);

    /**
     * 检查教练是否教授该课程的科目
     */
    @Select("<script>" +
            "SELECT COUNT(*) > 0 " +
            "FROM coach_subject cs " +
            "WHERE cs.coach_id = #{coachId} " +
            "AND cs.subject_id IN (" +
            "  SELECT DISTINCT " +
            "    CASE " +
            "      WHEN c.is_combined = 0 THEN c.subject_id " +
            "      ELSE csub.subject_id " +
            "    END " +
            "  FROM course c " +
            "  LEFT JOIN course_subject csub ON c.id = csub.course_id " +
            "  WHERE c.id = #{courseId} " +
            "  AND (c.is_combined = 0 OR csub.subject_id IS NOT NULL)" +
            ")" +
            "</script>")
    boolean isCoachQualifiedForCourse(@Param("coachId") Long coachId,
                                      @Param("courseId") Long courseId);
}