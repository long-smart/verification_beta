package com.xcdata.verification.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.constant verification
 * @Date: create in 2020.9.11 11:35
 */
@ConfigurationProperties(prefix = "cds")
@Component
@Data
public class CDSProperties {
    private String name;
    private String username;
    private String password;
}
