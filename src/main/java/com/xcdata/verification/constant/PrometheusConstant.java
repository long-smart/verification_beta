package com.xcdata.verification.constant;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.common verification
 * @Date: create in 2020.9.10 15:34
 */
public class PrometheusConstant {
    // 地址
    public static final String API = "api/v1";

    // query
    public static final String QUERY = "query";

    // 端口
    public static final String PORT = "9090";

    // 查看prometheus的target服务是否正常的指标
    public static final String UP = "up";

    public static final String UP_SQL = UP + "{job=~\"node.*|ceph.*\"}";


    // prometheus query查询地址
    public static final String URL = "http://%s:" + PORT + "/" + API + "/" + QUERY;


    public static final String STATUS = "status";

    public static final String DATA = "data";

    public static final String RESULT = "result";

    public static final String METRIC = "metric";

    public static final String INSTANCE = "instance";

    public static final String VALUE = "value";

    public static final String JOB = "job";

    // prometheus查询成功
    public static final String SUCCESS = "success";
}
