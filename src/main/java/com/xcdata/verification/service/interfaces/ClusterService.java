package com.xcdata.verification.service.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.xcdata.verification.constant.Result;
import com.xcdata.verification.entity.LoginResponse;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service verification
 * @Date: create in 2020.9.10 19:12
 */
public interface ClusterService {
    Result<JsonNode> getClusterStatus();

    Result<JsonNode> getOsd();

    Result<Map<String, LoginResponse>> getToken();
}
