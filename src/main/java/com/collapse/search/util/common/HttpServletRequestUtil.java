package com.collapse.search.util.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class HttpServletRequestUtil {

    public String getParam(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(httpServletRequest.getParameterMap());
    }

    public String getIp(HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-ForWarded-For");
        if(isIpEmptyOrUnknown(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if(isIpEmptyOrUnknown(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if(isIpEmptyOrUnknown(ip)) {
            ip = httpServletRequest.getHeader("HTTP_CLIENT_IP");
        }
        if(isIpEmptyOrUnknown(ip)) {
            ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(isIpEmptyOrUnknown(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }

    public static String getReferer(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("referer");
    }

    private static boolean isIpEmptyOrUnknown(String ip) {
        return isIpEmpty(ip) || isIpUnknown(ip);
    }

    private static boolean isIpEmpty(String ip) {
        return StringUtils.isEmpty(ip);
    }

    private static boolean isIpUnknown(String ip) {
        return "unknown".equalsIgnoreCase(ip);
    }
}
