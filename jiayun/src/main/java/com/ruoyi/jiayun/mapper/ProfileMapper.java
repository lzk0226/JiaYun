package com.ruoyi.jiayun.mapper;

import com.ruoyi.jiayun.domain.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProfileMapper {

    /**
     * 获取学员统计数据
     */
    @Select("SELECT completed_lessons, passed_subjects, total_subjects, " +
            "reservation_count, overall_progress " +
            "FROM statistics WHERE student_id = #{studentId}")
    @Results({
            @Result(property = "completedLessons", column = "completed_lessons"),
            @Result(property = "passedSubjects", column = "passed_subjects"),
            @Result(property = "totalSubjects", column = "total_subjects"),
            @Result(property = "reservationCount", column = "reservation_count"),
            @Result(property = "overallProgress", column = "overall_progress")
    })
    Statistics getStudentStatistics(@Param("studentId") Long studentId);

    /**
     * 获取学员的学习进度列表
     */
    @Select("SELECT lp.id, lp.student_id, lp.subject_id, lp.completed_lessons, " +
            "lp.progress_percent, lp.status, " +
            "s.subject_name, s.subject_code " +
            "FROM learning_progress lp " +
            "LEFT JOIN subject s ON lp.subject_id = s.id " +
            "WHERE lp.student_id = #{studentId} " +
            "ORDER BY s.sort_order")
    @Results({
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "completedLessons", column = "completed_lessons"),
            @Result(property = "progressPercent", column = "progress_percent"),
            @Result(property = "subject.id", column = "subject_id"),
            @Result(property = "subject.subjectName", column = "subject_name"),
            @Result(property = "subject.subjectCode", column = "subject_code")
    })
    List<LearningProgress> getStudentLearningProgress(@Param("studentId") Long studentId);

    /**
     * 获取学员的考试记录
     */
    @Select("SELECT e.id, e.student_id, e.subject_id, e.title, e.exam_place, " +
            "e.exam_time, e.score, e.status, " +
            "s.subject_name, s.subject_code " +
            "FROM exam e " +
            "LEFT JOIN subject s ON e.subject_id = s.id " +
            "WHERE e.student_id = #{studentId} " +
            "ORDER BY s.sort_order")
    @Results({
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "examPlace", column = "exam_place"),
            @Result(property = "examTime", column = "exam_time"),
            @Result(property = "subject.id", column = "subject_id"),
            @Result(property = "subject.subjectName", column = "subject_name"),
            @Result(property = "subject.subjectCode", column = "subject_code")
    })
    List<Exam> getStudentExams(@Param("studentId") Long studentId);

    /**
     * 获取学员的课程列表（含教练信息）
     */
    @Select("SELECT sc.id, sc.student_id, sc.course_id, sc.coach_id, " +
            "sc.enroll_date, sc.start_date, sc.completed_lessons, sc.total_lessons, " +
            "sc.progress, sc.status, " +
            "c.name as course_name, c.type, " +
            "coach.name as coach_name " +
            "FROM student_course sc " +
            "LEFT JOIN course c ON sc.course_id = c.id " +
            "LEFT JOIN coach ON sc.coach_id = coach.id " +
            "WHERE sc.student_id = #{studentId} " +
            "ORDER BY sc.enroll_date DESC")
    @Results({
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "coachId", column = "coach_id"),
            @Result(property = "enrollDate", column = "enroll_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "completedLessons", column = "completed_lessons"),
            @Result(property = "totalLessons", column = "total_lessons"),
            @Result(property = "course.name", column = "course_name"),
            @Result(property = "course.type", column = "type"),
            @Result(property = "coach.name", column = "coach_name")
    })
    List<StudentCourse> getStudentCourses(@Param("studentId") Long studentId);

    /**
     * 更新学员基本信息（动态更新，只更新非空字段）
     */
    @UpdateProvider(type = StudentSqlProvider.class, method = "updateStudentInfo")
    int updateStudentInfo(Student student);

    /**
     * 单独更新学员头像
     */
    @Update("UPDATE student SET avatar = #{avatar} WHERE id = #{id}")
    int updateAvatar(@Param("id") Long id, @Param("avatar") String avatar);

    /**
     * SQL提供者类，用于动态生成UPDATE语句
     */
    class StudentSqlProvider {
        public String updateStudentInfo(Student student) {
            StringBuilder sql = new StringBuilder("UPDATE student SET ");
            boolean hasField = false;

            if (student.getName() != null && !student.getName().isEmpty()) {
                sql.append("name = #{name}");
                hasField = true;
            }
            if (student.getGender() != null && !student.getGender().isEmpty()) {
                if (hasField) sql.append(", ");
                sql.append("gender = #{gender}");
                hasField = true;
            }
            if (student.getBirthdate() != null) {
                if (hasField) sql.append(", ");
                sql.append("birthdate = #{birthdate}");
                hasField = true;
            }
            if (student.getPhone() != null && !student.getPhone().isEmpty()) {
                if (hasField) sql.append(", ");
                sql.append("phone = #{phone}");
                hasField = true;
            }
            if (student.getIdcard() != null && !student.getIdcard().isEmpty()) {
                if (hasField) sql.append(", ");
                sql.append("idcard = #{idcard}");
                hasField = true;
            }
            if (student.getLicenseType() != null) {
                if (hasField) sql.append(", ");
                sql.append("license_type = #{licenseType}");
                hasField = true;
            }
            if (student.getAddress() != null) {
                if (hasField) sql.append(", ");
                sql.append("address = #{address}");
                hasField = true;
            }
            // 注意：这里不包含avatar字段，避免意外清空头像

            if (!hasField) {
                // 如果没有任何字段需要更新，返回一个不会影响数据的SQL
                sql.append("id = #{id}");
            }

            sql.append(" WHERE id = #{id}");
            return sql.toString();
        }
    }
}