package com.xcdata.verification.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: ZhiLong Li
 * @Description: 物理机表部分字段, 只拿需要的
 * @Date: create in 2020.9.10 14:13
 */
@Data
@Document("physical_hosts")
public class PhysicalHosts {
    @Id
    private String id;
    @Schema(title = "uuid")
    private String uuid;
    // 管理网段IP
    private String mgmtIp;
    // 物理机名称
    private String name;
    // 描述
    private String description;
}
