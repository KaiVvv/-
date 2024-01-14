package com.briup.cms.common.util;

import com.briup.cms.common.config.ConfigProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author YuYan
 * @date 2023-11-21 17:02:43
 */
@Data
@Component
public class QiniuUtil {

    private final ConfigProperties configProperties;

    public String upload(MultipartFile multipartFile) {
        try {
            return upload0(multipartFile);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String upload0(MultipartFile multipartFile) throws Exception {
        // 创建配置对象，选择机房区域
        Configuration conf = new Configuration(Region.autoRegion());
        // 创建一个上传管理器对象（可以理解为一个请求对象）
        UploadManager uploadManager = new UploadManager(conf);
        // 从MultipartFile对象中取出能够读取文件信息的输入流
        InputStream is = multipartFile.getInputStream();
        // 进行解密操作，获得上传凭证
        Auth auth = Auth.create(configProperties.getOssAccessKey(),
                configProperties.getOssSecretKey());
        // 获取到Token令牌字符串
        String upToken = auth.uploadToken(configProperties.getOssBucket());
        // 定义Key，表示文件在云空间中的名称
        // 将来要访问这个文件，就需要使用baseUrl + key
        // 如果上传时指定了key，则使用我们指定的key
        // 否则使用计算出来的hash值代替key
        // String key = "dog.webp";
        String key = multipartFile.getOriginalFilename();

        // 确保文件名称不重复
        key = randomFileName(key);
        // 通过上传管理器对象（请求对象），发送请求，得到响应
        Response response = uploadManager.put(is, key, upToken, null, null);
        // 将响应字符串转成Map集合
        StringMap stringMap = response.jsonToMap();
        // 取出Map中的Key字段值
        key = (String) stringMap.get("key");
        // 返回完整的图片访问URL（包含七牛云测试域名 + 图片访问Key）
        return configProperties.getOssBaseUrl() + key;
    }

    /**
     * 将传入的一个文件名称处理成带有UUID随机数内容的不重复名称
     * @param fileName
     * @return
     */
    private String randomFileName(String fileName) {

        if (ObjectUtil.anyNotHasText(fileName)) {
            fileName = configProperties.getOssDefaultFileName();
        }

        String[] split = fileName.split("\\.");
        // 生成一个UUID随机字符串
        String uuid = UUID.randomUUID().toString();
        if (split.length < 2) {
            // 如果拆分的结果长度小于2，说明文件名中可能不存在小数点（没有后缀）
            // 就把生成的uuid直接拼到文件名的后边
            fileName = fileName + "-" + uuid;
        } else if (split.length == 2) {
            // 如果正好拆分成两部分，则当做是前缀和后缀
            // 把uuid拼接在前缀后边
            String prefix = split[0];
            String suffix = split[1];
            fileName = prefix + "-" + uuid + "." + suffix;
        } else {
            // 执行到这里，说明数组长度大于2，源文件名中包含不止一个小数点
            // 通常，将最后一部分当成是真正的后缀，前面所有内容是前缀
            // a.b.c.jpg --split-->{"a", "b", "c", "jpg"}
            // 取出数组最后一个元素当做suffix
            String suffix = split[split.length - 1];
            // 将前面第[0]~[split.length-2]范围的元素使用小数点连接起来
            String prefix = String.join(".", Arrays.copyOf(split, split.length - 1));
            fileName = prefix + "-" + uuid + "." + suffix;
        }
        return fileName;
    }

}
