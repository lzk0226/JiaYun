package com.ruoyi.jiayun.mapper;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.CoachDetail;
import com.ruoyi.jiayun.domain.Review;
import com.ruoyi.jiayun.utils.StringListTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoachMapper {

    /**
     * 查询所有可用教练（基础信息）
     */
    @Select("SELECT id, name, title, badge, level, experience, students_count, " +
            "rating, reviews_count, pass_rate, features, avatar, description, " +
            "subjects_name, subjects_code " +
            "FROM v_coach_detail WHERE status = 1 " +
            "ORDER BY rating DESC, id ASC")
    @Results(id = "coachResultMap", value = {
            @Result(property = "studentsCount", column = "students_count"),
            @Result(property = "reviewsCount", column = "reviews_count"),
            @Result(property = "passRate", column = "pass_rate"),
            @Result(property = "subjectsName", column = "subjects_name"),
            @Result(property = "subjectsCode", column = "subjects_code"),
            @Result(property = "features", column = "features",
                    typeHandler = StringListTypeHandler.class)
    })
    List<Coach> selectAllCoaches();

    /**
     * 根据ID查询教练详情
     */
    @Select("SELECT id, name, title, badge, level, experience, students_count, " +
            "rating, reviews_count, pass_rate, features, avatar, description, " +
            "subjects_name, subjects_code " +
            "FROM v_coach_detail WHERE id = #{id} AND status = 1")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentsCount", column = "students_count"),
            @Result(property = "reviewsCount", column = "reviews_count"),
            @Result(property = "passRate", column = "pass_rate"),
            @Result(property = "subjectsName", column = "subjects_name"),
            @Result(property = "subjectsCode", column = "subjects_code"),
            @Result(property = "features", column = "features",
                    typeHandler = StringListTypeHandler.class),
            @Result(property = "reviews", column = "id",
                    many = @Many(select = "selectReviewsByCoachId"))
    })
    CoachDetail selectCoachDetailById(Long id);

    /**
     * 查询教练的评价列表
     */
    @Select("SELECT student_name, review_date, rating, content " +
            "FROM review " +
            "WHERE coach_id = #{coachId} " +
            "ORDER BY review_date DESC " +
            "LIMIT 10")
    @Results({
            @Result(property = "studentName", column = "student_name"),
            @Result(property = "reviewDate", column = "review_date"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "content", column = "content")
    })
    List<Review> selectReviewsByCoachId(Long coachId);

    /**
     * 根据科目筛选教练
     */
    @Select("<script>" +
            "SELECT id, name, title, badge, level, experience, students_count, " +
            "rating, reviews_count, pass_rate, features, avatar, description, " +
            "subjects_name, subjects_code " +
            "FROM v_coach_detail WHERE status = 1 " +
            "<if test='subjectId != null'>" +
            "AND EXISTS (" +
            "  SELECT 1 FROM coach_subject cs WHERE cs.coach_id = id AND cs.subject_id = #{subjectId}" +
            ")" +
            "</if>" +
            "ORDER BY rating DESC, id ASC" +
            "</script>")
    @ResultMap("coachResultMap")
    List<Coach> selectCoachesBySubject(@Param("subjectId") Integer subjectId);

    /**
     * 根据级别筛选教练
     */
    @Select("SELECT id, name, title, badge, level, experience, students_count, " +
            "rating, reviews_count, pass_rate, features, avatar, description, " +
            "subjects_name, subjects_code " +
            "FROM v_coach_detail WHERE status = 1 AND level = #{level} " +
            "ORDER BY rating DESC, id ASC")
    @ResultMap("coachResultMap")
    List<Coach> selectCoachesByLevel(String level);

    /**
     * 综合筛选教练
     */
    @Select("<script>" +
            "SELECT id, name, title, badge, level, experience, students_count, " +
            "rating, reviews_count, pass_rate, features, avatar, description, " +
            "subjects_name, subjects_code " +
            "FROM v_coach_detail WHERE status = 1 " +
            "<if test='subjectId != null'>" +
            "AND EXISTS (" +
            "  SELECT 1 FROM coach_subject cs WHERE cs.coach_id = id AND cs.subject_id = #{subjectId}" +
            ")" +
            "</if>" +
            "<if test='level != null and level != \"\"'>" +
            "AND level = #{level}" +
            "</if>" +
            "ORDER BY rating DESC, id ASC" +
            "</script>")
    @ResultMap("coachResultMap")
    List<Coach> selectCoachesWithFilters(@Param("subjectId") Integer subjectId,
                                         @Param("level") String level);
}