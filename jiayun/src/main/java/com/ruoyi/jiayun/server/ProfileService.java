package com.ruoyi.jiayun.server;
import com.ruoyi.jiayun.domain.Student;
import java.util.Map;

public interface ProfileService {

    /**
     * 获取学员完整信息（个人中心首页数据）
     */
    Map<String, Object> getStudentProfile(Long studentId);

    /**
     * 更新学员信息
     */
    boolean updateStudentInfo(Student student);
    /**
     * 单独更新学员头像
     * @param studentId 学员ID
     * @param avatar 头像数据（Base64字符串）
     * @return 是否更新成功
     */
    boolean updateAvatar(Long studentId, String avatar);
}