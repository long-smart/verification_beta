package com.xcdata.verification.service.interfaces;

import com.xcdata.verification.entity.PhysicalHosts;

import java.util.List;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service verification
 * @Date: create in 2020.9.10 14:18
 */
public interface PhysicalHostService {
    List<PhysicalHosts> getNodeList();
}
