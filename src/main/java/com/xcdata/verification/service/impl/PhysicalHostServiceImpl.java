package com.xcdata.verification.service.impl;

import com.xcdata.verification.entity.PhysicalHosts;
import com.xcdata.verification.service.interfaces.PhysicalHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service.impl verification
 * @Date: create in 2020.9.10 14:18
 */
@Service
public class PhysicalHostServiceImpl implements PhysicalHostService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<PhysicalHosts> getNodeList() {
        return mongoTemplate.findAll(PhysicalHosts.class);
    }
}
