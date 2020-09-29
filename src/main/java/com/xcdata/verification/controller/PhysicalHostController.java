package com.xcdata.verification.controller;

import com.xcdata.verification.entity.PhysicalHosts;
import com.xcdata.verification.service.interfaces.PhysicalHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.common verification
 * @Date: create in 2020.9.10 14:15
 */
@RestController
@RequestMapping("/host")
public class PhysicalHostController {
    @Autowired
    PhysicalHostService physicalHostService;


    /**
     * 获取所有物理主机
     * @return 主机列表
     */
    @GetMapping("/getNodeList")
    List<PhysicalHosts> getNodeList() {
        return physicalHostService.getNodeList();
    }

}
