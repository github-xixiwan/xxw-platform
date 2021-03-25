package com.xxw.platform.util.http;

import org.apache.http.cookie.Cookie;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * http请求类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class HttpRequest<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求body参数
     */
    private Map<String, String> params;

    /**
     * 请求header参数
     */
    private Map<String, String> requestHeader;

    /**
     * 请求cookie列表
     */
    private List<Cookie> cookieList;

    /**
     * 期望body返回对象类型
     */
    private Class<T> clazz;

    /**
     * body返回值是否为下划线编码
     */
    private Boolean isSnakeCase;

    /**
     * 请求超时时间
     */
    private Integer socketTimeout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Boolean getSnakeCase() {
        return isSnakeCase;
    }

    public void setSnakeCase(Boolean snakeCase) {
        isSnakeCase = snakeCase;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    @Override
    public String toString() {
        return "StoHttpRequest{" +
                "url='" + url + '\'' +
                ", params=" + params +
                ", requestHeader=" + requestHeader +
                ", cookieList=" + cookieList +
                ", clazz=" + clazz +
                ", isSnakeCase=" + isSnakeCase +
                ", socketTimeout=" + socketTimeout +
                '}';
    }
}
