package com.briup.cms.common.model.ext;

import com.briup.cms.common.model.entity.Log;
import com.briup.cms.common.model.vo.LogEntityVO;
import com.briup.cms.common.util.BeanUtil;
import com.briup.cms.common.util.ObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志 - 扩展模型对象
 *
 * 基于Entity实体，扩展入参和出参所需要的属性及方法
 * @author YuYan
 * @date 2023-11-30 15:09:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class LogExt extends Log {

    /**
     * 查询的开始时间和结束时间
     */
    private Date startTime;
    private Date endTime;
    private String username;
    private String requestUrl;
    private int limit;

    public static LogExt toExt(LogEntityVO logEntityVO) {
        return ObjectUtil.isNull(logEntityVO) ? null :
                LogExt.builder()
                        .requestUrl(logEntityVO.getRequestUrl())
                        .username(logEntityVO.getUsername())
                        .startTime(logEntityVO.getStartTime())
                        .endTime(logEntityVO.getEndTime())
                        .build();
    }

    public static List<LogExt> toExt(List<Log> logEntities) {
        return logEntities.stream()
                .map(LogExt::toExt)
                .collect(Collectors.toList());
    }

    public static LogExt toExt(Log log) {
        return BeanUtil.copyProperties(log, LogExt.class);
    }
}
