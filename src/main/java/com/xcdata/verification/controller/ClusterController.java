package com.xcdata.verification.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.xcdata.verification.constant.Result;
import com.xcdata.verification.entity.LoginResponse;
import com.xcdata.verification.service.interfaces.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: ceph相关接口, 接口中不对外暴露 ‘ceph’
 * @Date: create in 2020.9.10 13:05
 */
@RestController
@RequestMapping("/cluster")
public class ClusterController {
    @Autowired
    ClusterService clusterService;

    @GetMapping("/status")
    public Result<JsonNode> getClusterStatus() {
        return clusterService.getClusterStatus();
    }

    @GetMapping("/osdList")
    public Result<JsonNode> getOsd() {
        return clusterService.getOsd();
    }

    @GetMapping("/getAllNodeToken")
    public Result<Map<String, LoginResponse>> getToken() {
        return clusterService.getToken();
    }
}
