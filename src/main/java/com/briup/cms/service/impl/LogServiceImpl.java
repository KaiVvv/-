package com.briup.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Log;
import com.briup.cms.common.model.ext.LogExt;
import com.briup.cms.service.LogService;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * @author YuYan
 * @date 2024-01-09 19:42:56
 */
@Service
public class LogServiceImpl implements LogService {
    @Override
    public void save(Log log) {

    }

    @Override
    public IPage<LogExt> pageQueryByClause(IPage<Log> page, LogExt logExt) {
        return null;
    }

    @Override
    public void download(OutputStream os, LogExt logParam) {

    }
}
