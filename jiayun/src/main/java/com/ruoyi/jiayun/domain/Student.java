package com.ruoyi.jiayun.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:26
 * @Author : SoakLightDust
 */
@Data
public class Student {
    private Long id;
    private String userId;
    private String name;
    private String gender; // male/female
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String phone;
    private String idcard;
    private String licenseType; // C1/C2/B2/A2
    private String address;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;
    // 不包含 password, status, created_at, updated_at
}