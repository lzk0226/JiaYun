package com.ruoyi.jiayun.domain;
import lombok.Data;

@Data
public class Vehicle {
    private Long id;
    private String plateNumber;
    private String model;
    private String vehicleImage;
    private String status; // available/maintenance/retired
    // 不包含 created_at, updated_at
}
