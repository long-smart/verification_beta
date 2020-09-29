package com.xcdata.verification.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.common verification
 * @Date: create in 2020.9.10 15:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private boolean success;
    private T result;
    private String msg;

    public static <T> Result<T> success(T result, String msg) {
        return new Result<T>(true, result, msg);
    }

    public static <T> Result<T> error(T result, String msg) {
        return new Result<T>(false, result, msg);
    }
}
