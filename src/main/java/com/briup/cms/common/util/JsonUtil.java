package com.briup.cms.common.util;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author YuYan
 * @date 2023-11-27 16:31:25
 */
@Component
@RequiredArgsConstructor
public class JsonUtil {

    private final ObjectMapper mapper;

    public String stringify(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public <J> J parse(String jsonStr, Class<J> tClass) {
        try {
            return mapper.readValue(jsonStr, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SneakyThrows
    public Map<String, Object> parseForMap(String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }



}
