package com.briup.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Log;
import com.briup.cms.common.model.ext.LogExt;

import java.io.OutputStream;

/**
 * @author YuYan
 * @date 2023-12-08 14:10:53
 */
public interface LogService {

    void save(Log log);

    IPage<LogExt> pageQueryByClause(IPage<Log> page,
                                    LogExt logExt);

    void download(OutputStream os,
                  LogExt logParam);
}
