package com.xcdata.verification.controller;

import com.xcdata.verification.constant.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.controller verification
 * @Date: create in 2020.9.11 15:29
 */
@RestController
@RequestMapping("/samba")
public class SambaController {

    @PostMapping("/check")
    public Result<?> checkSamba(Map<String, String> params) {
        return null;
    }
}
