package com.briup.cms.common.model.vo;

import com.briup.cms.common.model.ext.LogExt;
import com.briup.cms.common.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视图对象 - 日志
 *
 * @author YuYan
 * @date 2023-11-30 09:22:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntityVO {


    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "businessName")
    private String businessName;
    @JsonProperty(value = "requestUrl")
    private String requestUrl;
    @JsonProperty(value = "ip")
    private String requestIp;
    @JsonProperty(value = "spendTime")
    private Long spendTime;
    @JsonProperty(value = "requestMethod")
    private String requestMethod;
    @JsonProperty(value = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonProperty(value = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startTime;
    @JsonProperty(value = "endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endTime;


    public static List<LogEntityVO> toVO(List<LogExt> logExts) {
        return ObjectUtil.isNull(logExts) ? null :
                logExts.stream()
                .map(LogEntityVO::toVO)
                .collect(Collectors.toList());
    }

    public static LogEntityVO toVO(LogExt logExt) {
        return ObjectUtil.isNull(logExt) ? null :
                LogEntityVO.builder()
                        .username(logExt.getUsername())
                        .requestUrl(logExt.getRequestUrl())
                        .businessName(logExt.getBusinessName())
                        .requestIp(logExt.getRequestIp())
                        .spendTime(logExt.getSpendTime())
                        .createTime(logExt.getCreateTime())
                        .requestMethod(logExt.getRequestMethod())
                        .startTime(logExt.getStartTime())
                        .endTime(logExt.getEndTime())
                        .build();
    }

}
