package com.xcdata.verification.controller;

import com.xcdata.verification.constant.Result;
import com.xcdata.verification.service.interfaces.PrometheusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: prometheus相关
 * @Date: create in 2020.9.10 11:42
 */
@RestController
@RequestMapping("/pro")
public class PrometheusController {
    @Autowired
    PrometheusService prometheusService;

    /**
     * 通过 up指标去查看 node_exporter和 ceph_exporter的状态
     * @return 返回所有节点的 node 和 ceph的状态
     */
    @GetMapping("/check")
    public Result<Map<String, Map<String, String>>> checkPrometheus() {
        return prometheusService.checkPrometheus();
    }
}
