package com.xcdata.verification.entity;

import lombok.Data;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.entity verification
 * @Date: create in 2020.9.11 11:58
 */

/**
 * nova或者 hesper 的登陆接口返回数据
 */
@Data
public class LoginResponse {
    private String code;
    private String msg;
    private String token;
    private String username;
    private String type;
}
