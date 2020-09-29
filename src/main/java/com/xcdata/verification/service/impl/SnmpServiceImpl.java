package com.xcdata.verification.service.impl;

import com.xcdata.verification.constant.Result;
import com.xcdata.verification.constant.SNMPConstant;
import com.xcdata.verification.entity.PhysicalHosts;
import com.xcdata.verification.service.interfaces.PhysicalHostService;
import com.xcdata.verification.service.interfaces.SnmpService;
import com.xcdata.verification.util.SNMPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service.impl verification
 * @Date: create in 2020.9.11 13:04
 */
@Service
public class SnmpServiceImpl implements SnmpService {

    @Autowired
    PhysicalHostService physicalHostService;

    /**
     * 通过 snmp4j包 获取mib树上的对象, 获取到说明服务正常
     * @return 各个节点的snmp服务异常状态
     */
    @Override
    public Result<Map<String, Boolean>> checkSNMP() {
        List<String> oids = new ArrayList<String>() {{
            add(SNMPConstant.OID1);
            add(SNMPConstant.OID2);
        }};
        List<PhysicalHosts> hosts = physicalHostService.getNodeList();
        Map<String, Boolean> result = new HashMap<>();
        if (hosts != null && hosts.size() > 0) {
            hosts.forEach(val -> {
                String address = String.format(SNMPConstant.ADDRESS, val.getMgmtIp());
                Map<String, String> var = SNMPUtil.getMibByOid(oids, address, SNMPConstant.COMMUNITY);
                if (var != null) {
                    result.put(val.getMgmtIp(), Boolean.FALSE);
                    // 这里比对通过oid获取到的mib对象的值, 如果true, 说明校验通过
                    if (SNMPConstant.MIB1.equals(var.get(SNMPConstant.OID_NEXT1)) && SNMPConstant.MIB2.equals(var.get(SNMPConstant.OID_NEXT2))) {
                        result.put(val.getMgmtIp(), Boolean.TRUE);
                    } else {
                        // 打印日志
                        System.out.println(val.getMgmtIp() + "比对不成功");
                    }
                }
            });
            return Result.success(result, "oid比对完成");
        } else {
            return Result.error(null, "节点信息获取失败");
        }
    }
}
