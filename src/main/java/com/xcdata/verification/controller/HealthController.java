package com.xcdata.verification.controller;

import com.xcdata.verification.constant.Result;
import com.xcdata.verification.service.interfaces.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.controller verification
 * @Date: create in 2020.9.11 17:01
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    HealthService healthService;

    @GetMapping("/checkService")
    public Result<Map<String, String>> checkService() {
        return healthService.checkService();
    }

    @GetMapping("/checkPort")
    public Result<Map<String, String>> checkPort() {
        return healthService.checkPort();
    }
}
