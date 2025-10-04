package com.ruoyi.jiayun.domain;

import lombok.Data;

/**
 * @version 1.0
 * 文件类型/说明:
 * 文件创建时间:2025/10/3下午 12:31
 * @Author : SoakLightDust
 */
@Data
public class Vehicle {
    private Long id;
    private String plateNumber;
    private String model;
    private String vehicleImage;
    private String status; // available/maintenance/retired
    // 不包含 created_at, updated_at
}
