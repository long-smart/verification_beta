package com.xcdata.verification.constant;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.common verification
 * @Date: create in 2020.9.10 13:01
 */
public class ClusterConstant {
    // ceph status
    public static final String CEPH_STATUS = "ceph status --format json-pretty";
    // osd
    public static final String CEPH_OSD_TREE = "ceph osd df tree --format json-pretty";

    // nova 或者 hesper 的登陆接口地址
    public static final String URL = "http://%s/%s/api/v1/user/login";
}
