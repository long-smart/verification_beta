package com.xcdata.verification.util;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.util verification
 * @Date: create in 2020.9.11 13:27
 */
public class SNMPUtil {
    public static Map<String, String> getMibByOid(List<String> oid, String address, String community) {
        try {
            CommunityTarget target = new CommunityTarget();
            Address adr = GenericAddress.parse(address);
            // 设定主机地址
            target.setAddress(adr);
            // 设置snmp团体，类似于密码
            target.setCommunity(new OctetString(community));
            // 设置超时重试次数
            target.setRetries(1);
            // 设置超时时间
            target.setTimeout(60);
            // 设置snmp版本
            target.setVersion(SnmpConstants.version2c);
            // 设置协议
            TransportMapping transportMapping = new DefaultUdpTransportMapping();
            // 调用TransportMapping中的listen()方法，启动监听进程，接收消息，由于该监听进程是守护进程，最后应调用close()方法来释放该进程
            transportMapping.listen();
            // 创建SNMP对象，用于发送请求PDU
            Snmp protocol = new Snmp(transportMapping);
            // 创建PDU, 获取mib
            PDU request = new PDU();

            // 添加 oid
            oid.forEach(val -> {
                request.add(new VariableBinding(new OID(val)));
            });
            // 调用setType()方法来确定该pdu的类型
            // GET NEXT 相当于 snmpgetnext -v2c -c public localhost 1.3.6.1.4.1.51052.1.2.0命令
            request.setType(PDU.GETNEXT);
            // 调用 send(PDU pdu,Target target)发送pdu，返回一个ResponseEvent对象
            ResponseEvent responseEvent = protocol.send(request, target);
            // 获得应答
            PDU response = responseEvent.getResponse();

            // 返回结果
            Map<String, String> result = new HashMap<>();
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    VariableBinding var = response.get(i);
                    result.put(String.valueOf(var.getOid()), String.valueOf(var.getVariable()));
                }
                transportMapping.close();
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
