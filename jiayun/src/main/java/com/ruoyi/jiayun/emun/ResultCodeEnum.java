package com.ruoyi.jiayun.emun;

public enum ResultCodeEnum {

    /* 成功状态码 */
    SUCCESS(200, "操作成功"),

    /* 客户端错误：4xx */
    FAIL(400, "操作失败"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "访问被禁止"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CONFLICT(409, "资源冲突"),
    VALIDATION_ERROR(422, "参数校验失败"),

    /* 服务端错误：5xx */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    /* 用户相关错误：1xxx */
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_NAME_EMPTY(1002, "用户名不能为空"),
    PASSWORD_EMPTY(1003, "密码不能为空"),
    NICK_NAME_EMPTY(1004, "昵称不能为空"),
    USER_NAME_EXISTS(1005, "用户名已存在"),
    EMAIL_EXISTS(1006, "邮箱已存在"),
    PHONE_EXISTS(1007, "手机号已存在"),
    LOGIN_FAILED(1008, "用户名或密码错误，或账号已被停用"),
    USER_ID_EMPTY(1009, "用户ID不能为空"),
    OLD_PASSWORD_EMPTY(1010, "旧密码不能为空"),
    NEW_PASSWORD_EMPTY(1011, "新密码不能为空"),
    PASSWORD_UPDATE_FAILED(1012, "密码修改失败，请检查旧密码是否正确"),

    /* 认证相关错误：4xxx */
    TOKEN_INVALID(4001, "Token无效或已过期"),
    TOKEN_EXPIRED(4002, "Token已过期"),
    TOKEN_MISSING(4003, "Token缺失"),
    TOKEN_BLACKLISTED(4004, "Token已被注销"),

    /* 业务操作相关：9xxx */
    REGISTER_SUCCESS(9001, "注册成功"),
    REGISTER_FAILED(9002, "注册失败"),
    UPDATE_SUCCESS(9003, "修改成功"),
    UPDATE_FAILED(9004, "修改失败"),
    PASSWORD_UPDATE_SUCCESS(9005, "密码修改成功"),
    ACCOUNT_DEACTIVATE_SUCCESS(9006, "账户注销成功"),
    ACCOUNT_DEACTIVATE_FAILED(9007, "账户注销失败");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据code获取枚举
     */
    public static ResultCodeEnum getByCode(Integer code) {
        for (ResultCodeEnum item : ResultCodeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 判断是否为成功状态码
     */
    public boolean isSuccess() {
        return SUCCESS.code.equals(this.code);
    }
}