package com.xxw.platform.util.http;

import org.apache.http.cookie.Cookie;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Http请求结果
 *
 * @author ethan
 * @since 2019/11/18
 */
public class HttpResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回的httpCode
     */
    private Integer code;

    /**
     * 返回的header
     */
    private Map<String, String> header;

    /**
     * 返回的body
     */
    private String entity;

    /**
     * 返回的body对象  转换为待处理对象
     */
    private T result;

    /**
     * 返回的cookie列表
     */
    private List<Cookie> cookieList;

    /**
     * 判断请求是否成功
     *
     * @return
     */
    public Boolean isSuccess() {
        return (this.code != null && this.code == 200);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
    }

    @Override
    public String toString() {
        return "StoHttpResponse{" +
                "code=" + code +
                ", header=" + header +
                ", entity='" + entity + '\'' +
                ", result=" + result +
                ", cookieList=" + cookieList +
                '}';
    }
}
