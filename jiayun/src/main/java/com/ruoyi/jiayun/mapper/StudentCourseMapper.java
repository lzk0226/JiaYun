package com.ruoyi.jiayun.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface StudentCourseMapper {

    @Insert("INSERT INTO student_course (student_id, course_id, coach_id, enroll_date, " +
            "total_lessons, completed_lessons, progress, status) " +
            "SELECT #{studentId}, #{courseId}, #{coachId}, #{enrollDate}, " +
            "c.duration, 0, 0.00, 'ongoing' " +
            "FROM course c WHERE c.id = #{courseId}")
    int insertStudentCourse(@Param("studentId") Long studentId,
                            @Param("courseId") Long courseId,
                            @Param("coachId") Long coachId,
                            @Param("enrollDate") LocalDate enrollDate);

    default int insertStudentCourse(Long studentId, Long courseId, Long coachId) {
        return insertStudentCourse(studentId, courseId, coachId, LocalDate.now());
    }
}