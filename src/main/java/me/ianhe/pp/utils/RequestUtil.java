package me.ianhe.pp.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * request工具类
 *
 * @author iHelin
 * @since 2017/9/1 17:14
 */
public class RequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    public static Optional<HttpServletRequest> getRequest() {
        Optional<HttpServletRequest> currentRequest = Optional.empty();
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                currentRequest = Optional.of(request);
            }
        } catch (IllegalStateException e) {
            logger.warn("Can not get currentRequest", e);
        }
        return currentRequest;
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

    private RequestUtil() {
        throw new UnsupportedOperationException("工具类不允许实例化");
    }
}
