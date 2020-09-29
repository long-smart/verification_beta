package com.xcdata.verification.service.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.xcdata.verification.constant.Result;

import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service verification
 * @Date: create in 2020.9.10 15:52
 */
public interface PrometheusService {
    Result<Map<String, Map<String, String>>> checkPrometheus();
}
