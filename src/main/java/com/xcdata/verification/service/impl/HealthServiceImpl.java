package com.xcdata.verification.service.impl;

import com.xcdata.verification.constant.HealthCheckConst;
import com.xcdata.verification.constant.Result;
import com.xcdata.verification.service.interfaces.HealthService;
import com.xcdata.verification.util.ShellUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.service.impl verification
 * @Date: create in 2020.9.11 17:03
 */
@Service
public class HealthServiceImpl implements HealthService {
    @Override
    public Result<Map<String, String>> checkService() {
        List<String> cmdList = null;
        cmdList = ShellUtil.execCmd(HealthCheckConst.CHECK_SERVICE);
        return Result.success(matchReg(cmdList), "命令执行完成");
    }

    @Override
    public Result<Map<String, String>> checkPort() {
        List<String> cmdList = ShellUtil.execCmd(HealthCheckConst.CHECK_PORT);
        return Result.success(matchReg(cmdList), "命令执行完成");
    }

    /**
     * 通过正则匹配出 [] 里面的内容，返回给前端
     * @return
     */
    private Map<String, String> matchReg(List<String> cmdList) {
        Map<String, String> result = new HashMap<>();

        cmdList.forEach(row -> {
            String[] textArr = row.split(":");

            if (textArr.length == 2) {
                textArr[0] = textArr[0].substring(10, textArr[0].length() - 8);
                System.out.println("第一个截取后的" + textArr[0]);

                Pattern p = Pattern.compile(HealthCheckConst.REG);
                Matcher m = p.matcher(textArr[0]);
                Matcher m2 = p.matcher(textArr[1]);
                String serviceName = "";
                String status = "";

                if (m.find()) {
                    serviceName = m.group(1);
                    System.out.println("serviceName =" + serviceName);
                    System.out.println(m);
                }
                if (m2.find()) {
                    status = m2.group(1);
                    System.out.println("status =" + status);
                }
                result.put(serviceName, status);
            }
        });

        return result;
    }

}
