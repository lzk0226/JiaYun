package com.ruoyi.jiayun.server.impl;
import com.ruoyi.jiayun.domain.Coach;
import com.ruoyi.jiayun.domain.CoachDetail;
import com.ruoyi.jiayun.mapper.CoachMapper;
import com.ruoyi.jiayun.server.CoachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachMapper coachMapper;

    @Override
    public List<Coach> getAllCoaches() {
        return coachMapper.selectAllCoaches();
    }

    @Override
    public List<Coach> getCoachesByFilters(String subject, String level, String sort) {
        Integer subjectId = null;

        // 处理科目筛选
        if (subject != null && !subject.isEmpty() && !"all".equals(subject)) {
            switch (subject) {
                case "subject1":
                    subjectId = 1;
                    break;
                case "subject2":
                    subjectId = 2;
                    break;
                case "subject3":
                    subjectId = 3;
                    break;
                case "subject4":
                    subjectId = 4;
                    break;
            }
        }

        // 查询教练
        List<Coach> coaches = coachMapper.selectCoachesWithFilters(subjectId, level);

        // 处理排序
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "rating":
                    coaches = coaches.stream()
                            .sorted(Comparator.comparing(Coach::getRating).reversed())
                            .collect(Collectors.toList());
                    break;
                case "experience":
                    coaches = coaches.stream()
                            .sorted(Comparator.comparing(Coach::getExperience).reversed())
                            .collect(Collectors.toList());
                    break;
                // default 保持数据库默认排序（按 rating DESC）
            }
        }

        return coaches;
    }

    @Override
    public CoachDetail getCoachDetail(Long id) {
        return coachMapper.selectCoachDetailById(id);
    }
}

