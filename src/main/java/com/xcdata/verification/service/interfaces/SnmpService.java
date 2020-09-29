package com.xcdata.verification.service.interfaces;

import com.xcdata.verification.constant.Result;

import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service verification
 * @Date: create in 2020.9.11 13:03
 */
public interface SnmpService {
    Result<Map<String, Boolean>> checkSNMP();
}
