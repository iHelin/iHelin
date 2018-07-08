package me.ianhe.isite.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Optional;

/**
 * request工具类
 *
 * @author iHelin
 * @since 2017/9/1 17:14
 */
public class RequestUtil {

    private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    public static Optional<HttpServletRequest> getRequest() {
        Optional<HttpServletRequest> currentRequest = Optional.empty();
        try {
            currentRequest = Optional.of(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        } catch (IllegalStateException e) {
            logger.warn("Can not get currentRequest", e);
        }
        return currentRequest;
    }

    public static String getDomain(HttpServletRequest request) {
        return request.getServerName();
    }

    public static String getCompleteRequestURL(HttpServletRequest request) {
        return getCompleteRequestURL(request, null);
    }

    public static String getCompleteRequestURL(HttpServletRequest request, String rmParam) {
        Assert.notNull(request, "the request must not be null.");
        StringBuilder sb = new StringBuilder(256);
        sb.append(request.getScheme()).append("://").append(request.getServerName())
                .append(':').append(request.getServerPort()).append(request.getContextPath())
                .append(request.getServletPath());
        Enumeration<String> names = request.getParameterNames();
        int i = 0;
        if (names != null) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                String value = request.getParameter(name);
                if (value == null || name.equals(rmParam)) {
                    continue;
                }
                sb.append(i++ == 0 ? '?' : '&').append(name).append('=').append(value);
            }
        }
        return sb.toString();
    }

    public static String getRequestData(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String data = null;
        try {
            data = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.info("getInputStream throw IOException: " + e.getMessage() + ", request's complete url: "
                    + getCompleteRequestURL(request));
        }
        return data;
    }

    /**
     * 获取请求真实ip
     *
     * @author iHelin
     * @since 2017/9/1 15:59
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        String unknownStr = "unknown";
        if (StringUtils.isNotBlank(ip) && !unknownStr.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !unknownStr.equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 判断是否是ajax请求
     *
     * @author iHelin
     * @since 2017/9/1 15:55
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (StringUtils.isNotBlank(request.getHeader("X-Requested-With"))
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    private RequestUtil() {
        //工具类不允许实例化
    }
}
