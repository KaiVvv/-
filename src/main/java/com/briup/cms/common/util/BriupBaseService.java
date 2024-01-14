package com.briup.cms.common.util;

import com.briup.cms.common.exception.CmsException;

/**
 * @author YuYan
 * @date 2023-11-30 15:22:07
 */
public interface BriupBaseService {

    default int checkResult(int result) {
        return checkResult(result, ResultCode.DATA_WRONG);
    }
    default int checkResult(int result, ResultCode resultCode) {
        if (result == 0) {
            throw new CmsException(resultCode);
        }
        return result;
    }

}
