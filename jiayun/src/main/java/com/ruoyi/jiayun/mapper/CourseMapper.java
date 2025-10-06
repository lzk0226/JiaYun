package com.ruoyi.jiayun.mapper;

import com.ruoyi.jiayun.domain.Course;
import com.ruoyi.jiayun.domain.CourseDetail;
import com.ruoyi.jiayun.utils.StringListTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {


    /**
     * 查询所有可用课程（基础信息）
     */
    @Select("SELECT id, name, subject_id, type, subtitle, badge, duration, period, " +
            "features, price, popular, students_count, is_combined, subjects_name " +
            "FROM v_course_detail WHERE status = 1 " +
            "ORDER BY popular DESC, id ASC")
    @Results(id = "courseResultMap", value = {
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "studentsCount", column = "students_count"),
            @Result(property = "isCombined", column = "is_combined"),
            @Result(property = "subjectsName", column = "subjects_name"),
            @Result(property = "features", column = "features",
                    typeHandler = StringListTypeHandler.class)
    })
    List<Course> selectAllCourses();

    /**
     * 根据ID查询课程详情
     */
    @Select("SELECT * FROM v_course_detail WHERE id = #{id}")
    @Results({
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "studentsCount", column = "students_count"),
            @Result(property = "isCombined", column = "is_combined"),
            @Result(property = "subjectsName", column = "subjects_name"),
            @Result(property = "features", column = "features",
                    typeHandler = StringListTypeHandler.class)
    })
    CourseDetail selectCourseDetailById(Long id);

    /**
     * 根据科目筛选课程
     */
    @Select("<script>" +
            "SELECT id, name, subject_id, type, subtitle, badge, duration, period, " +
            "features, price, popular, students_count, is_combined, subjects_name " +
            "FROM v_course_detail WHERE status = 1 " +
            "<if test='subjectId != null'>" +
            "AND (subject_id = #{subjectId} " +
            "OR (is_combined = 1 AND EXISTS (" +
            "  SELECT 1 FROM course_subject cs WHERE cs.course_id = id AND cs.subject_id = #{subjectId}" +
            ")))" +
            "</if>" +
            "<if test='isCombined != null'>" +
            "AND is_combined = #{isCombined}" +
            "</if>" +
            "ORDER BY popular DESC, id ASC" +
            "</script>")
    @ResultMap("courseResultMap")
    List<Course> selectCoursesBySubject(@Param("subjectId") Integer subjectId,
                                        @Param("isCombined") Boolean isCombined);

    /**
     * 根据类型筛选课程
     */
    @Select("SELECT id, name, subject_id, type, subtitle, badge, duration, period, " +
            "features, price, popular, students_count, is_combined, subjects_name " +
            "FROM v_course_detail WHERE status = 1 AND type = #{type} " +
            "ORDER BY popular DESC, id ASC")
    @ResultMap("courseResultMap")
    List<Course> selectCoursesByType(String type);

    /**
     * 综合筛选课程
     */
    @Select("<script>" +
            "SELECT id, name, subject_id, type, subtitle, badge, duration, period, " +
            "features, price, popular, students_count, is_combined, subjects_name " +
            "FROM v_course_detail WHERE status = 1 " +
            "<if test='subjectId != null'>" +
            "AND (subject_id = #{subjectId} " +
            "OR (is_combined = 1 AND EXISTS (" +
            "  SELECT 1 FROM course_subject cs WHERE cs.course_id = id AND cs.subject_id = #{subjectId}" +
            ")))" +
            "</if>" +
            "<if test='isCombined != null'>" +
            "AND is_combined = #{isCombined}" +
            "</if>" +
            "<if test='type != null and type != \"\"'>" +
            "AND type = #{type}" +
            "</if>" +
            "</script>")
    @ResultMap("courseResultMap")
    List<Course> selectCoursesWithFilters(@Param("subjectId") Integer subjectId,
                                          @Param("isCombined") Boolean isCombined,
                                          @Param("type") String type);

    /**
     * 更新课程报名人数
     */
    @Update("UPDATE course SET students_count = students_count + 1 WHERE id = #{courseId}")
    int updateStudentsCount(Long courseId);

    /**
     * 获取课程包含的科目ID列表
     * 单科课程返回subject_id，联报课程从course_subject表查询
     */
    @Select("<script>" +
            "SELECT DISTINCT " +
            "CASE " +
            "  WHEN c.is_combined = 0 THEN c.subject_id " +
            "  ELSE cs.subject_id " +
            "END as subject_id " +
            "FROM course c " +
            "LEFT JOIN course_subject cs ON c.id = cs.course_id " +
            "WHERE c.id = #{courseId} " +
            "AND (c.is_combined = 0 OR cs.subject_id IS NOT NULL)" +
            "</script>")
    List<Integer> getCourseSubjects(Long courseId);
}