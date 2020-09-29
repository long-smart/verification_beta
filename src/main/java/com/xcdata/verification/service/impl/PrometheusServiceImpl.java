package com.xcdata.verification.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xcdata.verification.constant.PrometheusConstant;
import com.xcdata.verification.constant.Result;
import com.xcdata.verification.service.interfaces.PrometheusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service.impl verification
 * @Date: create in 2020.9.10 15:53
 */
@Service
public class PrometheusServiceImpl implements PrometheusService {
    Logger logger = LoggerFactory.getLogger(PrometheusServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    /**
     * 从 prometheus获取 up指标下的 ceph_exporter 和 node_exporter的状态, 0代表服务异常, 1代表正常
     * @return { 192.168.1.226: { node: 1, ceph: 1 } }
     */
    @Override
    public Result<Map<String, Map<String, String>>> checkPrometheus() {
        String url = String.format(PrometheusConstant.URL, "192.168.1.226") + "?query={query}";
        Map<String, String> params = new HashMap<>();
        // 存放参数
        params.put(PrometheusConstant.QUERY, PrometheusConstant.UP_SQL);

        try {
            String res = restTemplate.getForObject(url, String.class, params);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(res);
            String status = jsonNode.get(PrometheusConstant.STATUS).asText();
            JsonNode result = jsonNode.get(PrometheusConstant.DATA).get(PrometheusConstant.RESULT);

            Map<String, Map<String, String>> map = new HashMap<>();
            if (!PrometheusConstant.SUCCESS.equals(status)) {
                return Result.error(null, "prometheus请求失败");
            }

            if (result.isArray()) {
                result.forEach(val -> {
                    JsonNode metric = val.get(PrometheusConstant.METRIC);
                    JsonNode value = val.get(PrometheusConstant.VALUE);

                    /*
                        用 job当做 key, 去保存prometheus target的状态。
                     */
                    if (metric.isObject() && value.isArray()) {
                        String instance = metric.get(PrometheusConstant.INSTANCE).asText();
                        if (instance != null) {
                            instance = instance.split(":")[0];
                            if (map.get(instance) != null) {
                                String job = metric.get(PrometheusConstant.JOB).asText();
                                map.get(instance).put(job, value.get(1).asText());
                            } else {
                                Map<String, String> statusMap = new HashMap<>();
                                String job = metric.get(PrometheusConstant.JOB).asText();
                                statusMap.put(job, value.get(1).asText());
                                map.put(instance, statusMap);
                            }
                        }
                    }

                });
                return Result.success(map, "所有节点的node_exporter和ceph_exporter状态获取成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(null, "失败");
    }
}
