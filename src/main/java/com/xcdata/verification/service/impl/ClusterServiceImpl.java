package com.xcdata.verification.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xcdata.verification.constant.CDSProperties;
import com.xcdata.verification.constant.ClusterConstant;
import com.xcdata.verification.constant.Result;
import com.xcdata.verification.entity.LoginResponse;
import com.xcdata.verification.entity.PhysicalHosts;
import com.xcdata.verification.service.interfaces.ClusterService;
import com.xcdata.verification.service.interfaces.PhysicalHostService;
import com.xcdata.verification.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service.impl verification
 * @Date: create in 2020.9.10 19:13
 */
@Service
public class ClusterServiceImpl implements ClusterService {
    @Autowired
    CDSProperties cdsProperties;

    @Autowired
    PhysicalHostService physicalHostService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 通过命令获取 ceph 状态
     * @return 返回 ceph status 命令的 json输出
     */
    @Override
    public Result<JsonNode> getClusterStatus() {
        StringBuilder cmdText = new StringBuilder();
        List<String> cmdList = ShellUtil.execCmd(ClusterConstant.CEPH_STATUS);
        cmdList.forEach(cmdText::append);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Result.success(objectMapper.readTree(cmdText.toString()), "集群状态获取成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(null, "集群状态获取失败");
    }

    /**
     * 执行 ceph osd tree 命令， 获取osd
     * @return 命令的json输出
     */
    @Override
    public Result<JsonNode> getOsd() {
        StringBuilder cmdText = new StringBuilder();
        List<String> cmdList = ShellUtil.execCmd(ClusterConstant.CEPH_OSD_TREE);
        cmdList.forEach(cmdText::append);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Result.success(objectMapper.readTree(cmdText.toString()), "osd获取成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(null, "osd获取失败");
    }

    /**
     * 获取所有节点的token, 返回给前端, 用于校验 hesper 或者 nova服务是否正常
     * @return 返回所有节点的token
     */
    @Override
    public Result<Map<String, LoginResponse>> getToken() {
        // 获取所有物理机节点
        List<PhysicalHosts> nodes = physicalHostService.getNodeList();
        // post 参数
        Map<String, String> params = new HashMap<String, String>(){{
            put("username", cdsProperties.getUsername());
            put("password", cdsProperties.getPassword());
        }};

        Map<String, LoginResponse> result = new HashMap<>();

        if (nodes != null) {
            nodes.forEach(val -> {
                String url = String.format(ClusterConstant.URL, val.getMgmtIp(), cdsProperties.getName());
                LoginResponse value = restTemplate.postForObject(url, params, LoginResponse.class);
                result.put(val.getMgmtIp(), value);
            });
            return Result.success(result, "token获取成功");
        }
        return Result.error(null, "token获取失败");
    }
}
