package com.xcdata.verification.service.interfaces;

import com.xcdata.verification.constant.Result;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service.interfaces verification
 * @Date: create in 2020.9.11 17:02
 */
public interface HealthService {
    Result<Map<String, String>> checkService();
    Result<Map<String, String>> checkPort();
}
