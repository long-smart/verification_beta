package com.xcdata.verification.util;

import com.xcdata.verification.constant.HealthCheckConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.util verification
 * @Date: create in 2020.9.10 17:50
 */
public class ShellUtil {
    public static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    /**
     * 执行linux命令
     * @param cmdStr
     * @return
     */
    public static List<String> execCmd(String cmdStr){
        List<String> result = new ArrayList<>();
        BufferedReader in = null;
        Process process = null;

        String[] cmd = new String[3];
        cmd[0] = "/bin/sh";
        cmd[1] = "-c";
        cmd[2] = cmdStr;
        try {
            process = Runtime.getRuntime().exec(cmd);
            in = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                result.add(line);
            }
            if (process.waitFor() != 0) {
                logger.error("{} 命令失败 waitFor = {}", cmd, process.waitFor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeIO(in);
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    public static void closeIO(Closeable... streams) {
        for (Closeable stream: streams) {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String text1 = "Check service [      qemu-kvm      ] ";
        String text2 = " \t\t ---------- [ OK ]------";

        Pattern p = Pattern.compile(HealthCheckConst.REG);
        Matcher m = p.matcher(text1);
        Matcher m2 = p.matcher(text2);

        String serviceName = "";
        String status = "";
        if (m.find()) {
            serviceName = m.group(1);
            System.out.println("serviceName =" + serviceName);
        } else {
            System.out.println("不匹配");
        }
        if (m2.find()) {
            status = m2.group(1);
            System.out.println("status =" + status);
        }
    }
}
