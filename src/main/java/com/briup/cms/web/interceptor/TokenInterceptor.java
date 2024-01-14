package com.briup.cms.web.interceptor;

import com.briup.cms.common.util.GlobalConstants;
import com.briup.cms.common.util.IpUtil;
import com.briup.cms.common.util.JwtUtil;
import com.briup.cms.common.util.RequestInfoHolder;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Token拦截器
 * @author YuYan
 * @date 2023-11-27 14:34:50
 */
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    /**
     * Token工具
     */
    private final JwtUtil jwtUtil;
    /**
     * IP工具
     */
    private final IpUtil ipUtil;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {

        /*
         * 如果不是映射到接口方法的请求也直接放行
         * （这个方式可以让静态资源请求通过，也可以让跨域预检请求通过）
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        /* 从请求对象中获取到请求方法 */
        String method = request.getMethod();

        /* 从请求头部中取出Token字符串 */
        String token = request.getHeader(GlobalConstants.TOKEN_HEADER_NAME);

        /* 如果可以通过验证，就解析出Token中包含的用户信息 */
        Map<String, Object> claims = jwtUtil.getClaims(token);
        /* 将请求相关信息、用户相关信息全部存入ThreadLocal中存储 */
        Object idObj = claims.get("userId");
        if (idObj instanceof Long) {
            RequestInfoHolder.set("userId", idObj);
        }
        Object usernameObj = claims.get("username");
        if (usernameObj instanceof String) {
            RequestInfoHolder.set("username", usernameObj);
        }
        /* 将HTTP请求相关的信息获取到，存入ThreadLocal中 */
        RequestInfoHolder.set("requestMethod", method);
        RequestInfoHolder.set("requestUrl", request.getRequestURI());
        String requestIp = request.getRemoteAddr();
        String requestSource = ipUtil.parseSource(requestIp);
        RequestInfoHolder.set("requestIp", requestIp);
        RequestInfoHolder.set("requestSource", requestSource);
        return true;
    }
}
