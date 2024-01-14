package com.briup.cms.common.util;

import com.briup.cms.common.exception.CmsException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Token令牌工具类，用于生成和校验Token令牌
 * @author YuYan
 * @date 2023-11-23 10:47:54
 */
@Component
public class JwtUtil {

    // 签名密钥
    private final String secret = "briup-jwt-secret";
    // 有效时长（单位：分钟）
    private final int expire = 24 * 60;

    // 生成令牌
    public String generate(Map<String, Object> claims) {
        return Jwts.builder()
                // 设置载荷
                .setClaims(claims)
                // 设置过期时间
                .setExpiration(expiration())
                // 设置算法类型和签名加密密钥
                .signWith(SignatureAlgorithm.HS256, secret)
                // 生成令牌
                .compact();
    }

    /**
     * 获取某个JWT中的载荷的方法
     * @param token
     * @return
     */
    public Map<String, Object> getClaims(String token) {
        // 判断令牌是否为空
        if (ObjectUtil.notHasText(token)) {
            throw new CmsException(ResultCode.TOKEN_EMPTY);
        }
        try {
            JwtParser parser = parser();

            parser.parse(token);

            return parser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new CmsException(ResultCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            throw new CmsException(ResultCode.TOKEN_SIGNATURE_ERROR);
        }
    }

    private JwtParser parser() {
        return Jwts.parser().setSigningKey(secret);
    }

    private Date expiration() {
        // 获取表示当前时间的Calendar对象
        Calendar c = Calendar.getInstance();
        // 加上有效时长
        c.add(Calendar.MINUTE, expire);
        // 转换为Date对象并返回
        return c.getTime();
    }

}







