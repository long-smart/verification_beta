package com.xcdata.verification.controller;

import com.xcdata.verification.constant.Result;
import com.xcdata.verification.service.interfaces.SnmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.controller verification
 * @Date: create in 2020.9.11 13:03
 */
@RestController
@RequestMapping("/snmp")
public class SnmpController {
    @Autowired
    SnmpService snmpService;

    @GetMapping("/checkService")
    public Result<Map<String, Boolean>>  checkSNMP() {
        return snmpService.checkSNMP();
    }
}
