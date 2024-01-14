package com.briup.cms.common.util;

import com.briup.cms.common.config.ConfigProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

/**
 * IP工具类
 *
 * 解析某个IP的归属地
 *
 * @author YuYan
 * @date 2023-12-08 16:31:10
 */
@Data
@Component
public class IpUtil {

    /**
     * 自定义配置对象
     */
    private final ConfigProperties configProperties;

    private final static String UNKNOWN = "未知归属地";

    private final static List<String> LOCAL_HOSTS = Arrays.asList(
            "localhost",
            "0:0:0:0:0:0:0:1",
            "127.0.0.1"
    );

    /**
     * 解析IP地址的归属地信息
     *
     * @return
     */
    public String parseSource(String ip) {
        try {
            return internalParseSource(ip);
        } catch (Exception e) {
            e.printStackTrace();
            return UNKNOWN;
        }
    }

    /**
     * @param ip
     * @return
     * @throws Exception
     */
    private String internalParseSource(String ip) throws Exception {
        /* 如果传入的地址是本机回环地址，则直接返回固定内容 */
        if (LOCAL_HOSTS.contains(ip)) {
            return "本机地址";
        }
        /* 拼接完整的请求地址（URL+参数）*/
        String requestAddress = configProperties.getIpQueryServerHost() + "?json=true&ip=" + ip;
        /* 创建URL对象 */
        URL url = new URL(requestAddress);
        /* 创建连接对象 */
        URLConnection connection = url.openConnection();
        /* 建立连接 */
        connection.connect();
        /* 获取输入流准备读取响应 */
        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "GBK"));
        String line;
        /* 创建一个可变长字符串对象，用于拼接读到的字符串内容 */
        StringBuilder body = new StringBuilder();
        while ((line = br.readLine()) != null) {
            body.append(line);
        }
        /* 将可变长字符串转换为String类型并返回 */
        return body.toString();
    }

}
