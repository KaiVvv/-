package com.briup.cms.common.util;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO流工具类
 * @author YuYan
 * @date 2023-12-15 17:22:22
 */
public class IOUtil {
    /**
     * 将输入流中所有数据读出写入到指定输出流中
     * @param is 输入流
     * @param os 输出流
     */
    @SneakyThrows
    public static void copyData(InputStream is, OutputStream os) {
        /* 标准IO流读写复制数据过程 */
        byte[] buf = new byte[1 << 10];
        int len;
        while ((len = is.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        is.close();
        os.flush();
        os.close();
    }

}
