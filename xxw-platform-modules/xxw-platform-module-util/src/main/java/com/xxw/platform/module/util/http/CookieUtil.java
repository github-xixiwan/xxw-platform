package com.xxw.platform.module.util.http;


import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie相关基础类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class CookieUtil {

    /**
     * 根据请求的cookie名字获取cookie对象
     *
     * @param request  请求对象
     * @param name     cookie名字
     * @return         获取到cookie对象，没有则返回null
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将请求的cookie封装到Map里面
     *
     * @param request   请求对象
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 保存Cookies
     * @param response  返回对象
     * @param name      cookie的key
     * @param value     设置的cookie值
     * @param time      失效时间
     * @return          生成的cookie
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time) throws UnsupportedEncodingException {
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        return response;
    }

}
