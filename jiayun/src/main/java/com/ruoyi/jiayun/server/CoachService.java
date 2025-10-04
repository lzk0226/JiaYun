package com.ruoyi.jiayun.server;

import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.CoachDetail;

import java.util.List;

public interface CoachService {

    /**
     * 获取所有教练列表
     */
    List<Coach> getAllCoaches();

    /**
     * 根据筛选条件获取教练
     * @param subject 科目代码（subject2/subject3/all）
     * @param level 教练级别（gold/excellent/normal）
     * @param sort 排序方式（default/rating/experience）
     */
    List<Coach> getCoachesByFilters(String subject, String level, String sort);

    /**
     * 根据ID获取教练详情
     */
    CoachDetail getCoachDetail(Long id);
}