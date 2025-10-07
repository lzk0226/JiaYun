package com.ruoyi.jiayun.controller;

import com.ruoyi.jiayun.R.Result;
import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.CoachDetail;
import com.ruoyi.jiayun.server.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/coaches")
@Tag(name = "教练管理", description = "教练相关接口")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping
    @Operation(summary = "获取教练列表", description = "支持科目、级别、排序筛选")
    public Result<List<Coach>> getCoaches(
            @Parameter(description = "科目代码: subject2/subject3/all")
            @RequestParam(required = false) String subject,
            @Parameter(description = "教练级别: gold/excellent/normal")
            @RequestParam(required = false) String level,
            @Parameter(description = "排序方式: default/rating/experience")
            @RequestParam(required = false, defaultValue = "default") String sort) {

        log.info("查询教练列表: subject={}, level={}, sort={}", subject, level, sort);

        List<Coach> coaches = coachService.getCoachesByFilters(subject, level, sort);
        return Result.success(coaches);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取教练详情")
    public Result<CoachDetail> getCoachDetail(
            @Parameter(description = "教练ID")
            @PathVariable Long id) {

        log.info("查询教练详情: id={}", id);

        CoachDetail detail = coachService.getCoachDetail(id);
        if (detail == null) {
            return Result.error("教练不存在");
        }
        return Result.success(detail);
    }
}
