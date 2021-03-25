package com.xxw.platform.util.http;

import com.xxw.platform.util.collection.ListUtil;
import com.xxw.platform.util.exception.UtilException;
import com.xxw.platform.util.json.JsonUtil;
import com.xxw.platform.util.map.MapUtil;
import com.xxw.platform.util.result.Result;
import com.xxw.platform.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTTP处理类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class HttpUtil {

    private static final int TIMEOUT = 500;
    private static final int SOCKET_TIMEOUT = 3000;

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    private static final String APPLICATION_JSON = "application/json; charset=utf-8";
    private static RequestConfig requestConfig = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static final int STATUS_OK = 200;

    /**
     * http headers
     */
    private static final String[] IP_HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    static {
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .build();

        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("http util init failed : " + e.getMessage());
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(1024);
        cm.setDefaultMaxPerRoute(100);
    }

    /**
     * HTTP Get 获取内容（最常用）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params) {
        HttpResponse stoHttpResponse = null;
        try {
            stoHttpResponse = doGetDetail(url, params, null, Boolean.TRUE);
        } catch (Exception e) {
            logger.error("Do get failed, request url: " + url + " , request params is: " + JsonUtil.toJson(params), e);
        }
        if (stoHttpResponse == null) {
            return null;
        }
        if (!stoHttpResponse.isSuccess()) {
            logger.info("HttpClient:get request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " error status code :" + stoHttpResponse.getCode() + " ,msg: " + stoHttpResponse.getEntity());
        }
        return stoHttpResponse.getEntity();
    }

    /**
     * HTTP Get 获取内容（需要带上header）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static String doGetWithHeader(String url, Map<String, String> params, Map<String, String> requestHeader) {
        HttpResponse stoHttpResponse = null;
        try {
            stoHttpResponse = doGetDetailWithAllInfo(url, params, requestHeader, null, null, Boolean.TRUE, null);
        } catch (Exception e) {
            logger.error("Do get failed, request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " , request header is: " + JsonUtil.toJson(requestHeader) + " error: " + e.getLocalizedMessage());
        }
        if (stoHttpResponse == null) {
            return null;
        }
        if (!stoHttpResponse.isSuccess()) {
            logger.info("HttpClient:get request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " , request header is: " + JsonUtil.toJson(requestHeader)
                    + " error status code :" + stoHttpResponse.getCode() + " ,msg: " + stoHttpResponse.getEntity());
        }
        return stoHttpResponse.getEntity();
    }

    /**
     * HTTP Get 获取内容（需要异常信息）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static String doGetWithEx(String url, Map<String, String> params) throws Exception {
        HttpResponse stoHttpResponse = doGetDetail(url, params, null, Boolean.TRUE);
        if (stoHttpResponse == null) {
            return null;
        }
        if (!stoHttpResponse.isSuccess()) {
            logger.info("HttpClient:get request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " error status code :" + stoHttpResponse.getCode() + " ,msg: " + stoHttpResponse.getEntity());
        }
        return stoHttpResponse.getEntity();
    }

    /**
     * HTTP Get 获取内容（解析alispData）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static <T> HttpResponse<T> doGetDetail(String url, Map<String, String> params, Class<T> clazz, Boolean isSnakeCase) throws Exception {
        return doGetDetailWithAllInfo(url, params, null, null, clazz, isSnakeCase, null);
    }

    /**
     * HTTP Get 获取内容（基础方法，除了上述参数外，可带上cookie，可设置超时时间）
     *
     * @param url
     * @param params
     * @param requestHeader
     * @param cookieList
     * @param clazz
     * @param isSnakeCase
     * @param socketTimeout
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> HttpResponse<T> doGetDetailWithAllInfo(String url, Map<String, String> params, Map<String, String> requestHeader, List<Cookie> cookieList, Class<T> clazz, Boolean isSnakeCase, Integer socketTimeout) throws Exception {
        url = appendParam(url, params);
        HttpGet httpGet = new HttpGet(url);

        return executeHttpRequest(url, params, requestHeader, cookieList, clazz, isSnakeCase, socketTimeout, httpGet, "get");
    }

    /**
     * HTTP Get 获取内容（基础方法，除了上述参数外，可带上cookie，可设置超时时间）
     *
     * @param stoHttpRequest
     * @return
     * @throws Exception
     */
    public static <T> HttpResponse<T> doGetDetailWithAllInfo(HttpRequest<T> stoHttpRequest) throws Exception {
        String url = stoHttpRequest.getUrl();
        url = appendParam(url, stoHttpRequest.getParams());
        HttpGet httpGet = new HttpGet(url);

        return executeHttpRequest(stoHttpRequest.getUrl(), stoHttpRequest.getParams(), stoHttpRequest.getRequestHeader(),
                stoHttpRequest.getCookieList(), stoHttpRequest.getClazz(), stoHttpRequest.getSnakeCase(), stoHttpRequest.getSocketTimeout(), httpGet, "get");
    }

    /**
     * HTTP Post 获取内容（最常用）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params) {
        HttpResponse stoHttpResponse = null;
        try {
            stoHttpResponse = doPostDetail(url, params, null, Boolean.TRUE);
        } catch (Exception e) {
            logger.error("Do post failed, request url: " + url + " , request params is: " + JsonUtil.toJson(params), e);
        }
        if (stoHttpResponse == null) {
            return null;
        }
        if (!stoHttpResponse.isSuccess()) {
            logger.info("HttpClient:post request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " error status code :" + stoHttpResponse.getCode() + " ,msg: " + stoHttpResponse.getEntity());
        }
        return stoHttpResponse.getEntity();
    }

    /**
     * HTTP Post 获取内容（需要带上header）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static String doPostWithHeader(String url, Map<String, String> params, Map<String, String> requestHeader) {
        HttpResponse stoHttpResponse = null;
        try {
            stoHttpResponse = doPostDetailWithAllInfo(url, params, requestHeader, null, null, Boolean.TRUE, null);
        } catch (Exception e) {
            logger.error("Do post failed, request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " , request header is: " + JsonUtil.toJson(requestHeader) + " error: " + e.getLocalizedMessage());
        }
        if (stoHttpResponse == null) {
            return null;
        }
        if (!stoHttpResponse.isSuccess()) {
            logger.info("HttpClient:post request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " , request header is: " + JsonUtil.toJson(requestHeader)
                    + " error status code :" + stoHttpResponse.getCode() + " ,msg: " + stoHttpResponse.getEntity());
        }
        return stoHttpResponse.getEntity();
    }

    /**
     * HTTP Post 获取内容（需要异常信息）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static String doPostWithEx(String url, Map<String, String> params) throws Exception {
        HttpResponse stoHttpResponse = doPostDetail(url, params, null, Boolean.TRUE);
        if (stoHttpResponse == null) {
            return null;
        }
        if (!stoHttpResponse.isSuccess()) {
            logger.info("HttpClient:post request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                    + " error status code :" + stoHttpResponse.getCode() + " ,msg: " + stoHttpResponse.getEntity());
        }
        return stoHttpResponse.getEntity();
    }

    /**
     * HTTP Post 获取内容（解析alispData）
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static <T> HttpResponse<T> doPostDetail(String url, Map<String, String> params, Class<T> clazz, Boolean isSnakeCase) throws Exception {
        return doPostDetailWithAllInfo(url, params, null, null, clazz, isSnakeCase, null);
    }

    /**
     * HTTP Post 获取内容（基础方法，除了上述参数外，可带上cookie，可设置超时时间）
     *
     * @param url
     * @param params
     * @param requestHeader
     * @param cookieList
     * @param clazz
     * @param isSnakeCase
     * @param socketTimeout
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> HttpResponse<T> doPostDetailWithAllInfo(String url, Map<String, String> params, Map<String, String> requestHeader, List<Cookie> cookieList, Class<T> clazz, Boolean isSnakeCase, Integer socketTimeout) throws Exception {
        List<NameValuePair> pairs = null;
        if (MapUtil.isNotEmpty(params)) {
            pairs = assemblePair(params);
        }
        HttpPost httpPost = new HttpPost(url);
        if (!ListUtil.isEmpty(pairs)) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        } else {
            pairs = new ArrayList<>();
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        }

        return executeHttpRequest(url, params, requestHeader, cookieList, clazz, isSnakeCase, socketTimeout, httpPost, "post");

    }

    /**
     * HTTP Post 获取内容（基础方法，除了上述参数外，可带上cookie，可设置超时时间）
     *
     * @param stoHttpRequest
     * @return
     * @throws Exception
     */
    public static <T> HttpResponse<T> doPostDetailWithAllInfo(HttpRequest<T> stoHttpRequest) throws Exception {
        List<NameValuePair> pairs = null;
        if (MapUtil.isNotEmpty(stoHttpRequest.getParams())) {
            pairs = assemblePair(stoHttpRequest.getParams());
        }
        HttpPost httpPost = new HttpPost(stoHttpRequest.getUrl());
        if (!ListUtil.isEmpty(pairs)) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        } else {
            pairs = new ArrayList<>();
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        }

        return executeHttpRequest(stoHttpRequest.getUrl(), stoHttpRequest.getParams(), stoHttpRequest.getRequestHeader(),
                stoHttpRequest.getCookieList(), stoHttpRequest.getClazz(), stoHttpRequest.getSnakeCase(), stoHttpRequest.getSocketTimeout(), httpPost, "post");

    }

    public static String postJson(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse response = null;
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        try {
            StringEntity se = new StringEntity(json, StandardCharsets.UTF_8);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            logger.info("HttpClient : post request url: " + url + " , params is: " + json);
            response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            String msg = EntityUtils.toString(response.getEntity(), "utf-8");
            if (statusCode != STATUS_OK) {
                httpPost.abort();
                logger.info("HttpClient,error status code :" + statusCode + " ,msg" + msg);
                throw new UtilException("HttpClient,error status code :" + statusCode + " ,msg : " + msg);
            }

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = msg;
            }
            EntityUtils.consume(entity);
            logger.info("HttpClient : post response: " + result);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.info("HttpClient : post response: " + url);
            }

        }
        return null;
    }

    public static String filterUrl(String baseUrl, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(baseUrl)) {
            return null;
        }
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = assemblePair(params);
            try {
                baseUrl += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return baseUrl;
        }
        return baseUrl;
    }

    public static String concatUrl(String baseUrl, Map<String, String> params) {
        if (StringUtils.isBlank(baseUrl)) {
            return null;
        }
        if (params != null && !params.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    sb.append("&" + entry.getKey() + "=" + value);
                }
            }
            try {
                baseUrl += "?" + sb.toString().substring(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return baseUrl;
        }
        return baseUrl;
    }

    public static String urlParam(Map<String, String> params, String charset) {
        String url = null;
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = assemblePair(params);
            try {
                url = EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * 仅仅根据字符串格式判断是否是正确的URL地址
     *
     * @param url URL地址
     * @return 如果是URL则返回TRUE，否则返回FALSE
     */
    public static Boolean isUrlByString(String url) {
        if (StringUtil.isEmpty(url)) {
            return false;
        }
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(url);
        return mat.find();
    }

    /**
     * 给URL增加参数,若原链接重存在与新增参数同名参数，则发生替换
     *
     * @param strUrl 原URL
     * @param param  需要新增到URL中的参数
     * @return 新的地址
     */
    public static String appendParam(String strUrl, Map<String, String> param) {
        if (StringUtil.isEmpty(strUrl) || !isUrlByString(strUrl)) {
            return null;
        }
        URI uri = null;
        try {
            uri = new URI(strUrl);
        } catch (URISyntaxException e) {
            return null;
        }
        Map<String, String> allParam = parseParam(strUrl);
        if (null == allParam) {
            allParam = new TreeMap<>();
        }
        if (null != param) {
            allParam.putAll(param);
        }
        URIBuilder builder = new URIBuilder(uri);
        builder.clearParameters();
        for (String key : allParam.keySet()) {
            builder.addParameter(key, allParam.get(key));
        }
        return builder.toString();
    }

    /**
     * 解析URL中所有参数并返回具体参数对
     *
     * @param strUrl URL地址
     * @return 解析出来的参数对
     */
    public static Map<String, String> parseParam(String strUrl) {
        if (StringUtil.isEmpty(strUrl) || !isUrlByString(strUrl)) {
            return null;
        }
        URI uri = null;
        try {
            uri = new URI(strUrl);
        } catch (URISyntaxException e) {
            return null;
        }
        List<NameValuePair> list = URLEncodedUtils.parse(uri, "UTF-8");
        if (ListUtil.isEmpty(list)) {
            return null;
        }
        Map<String, String> paramMap = new HashMap<>(16);
        for (NameValuePair nameValuePair : list) {
            paramMap.put(nameValuePair.getName(), nameValuePair.getValue());
        }
        return paramMap;
    }

    /**
     * 获取请求来源IP
     *
     * @param request
     * @return IP，找不到时为null
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        boolean found = false;
        String ip = null;
        for (int index = 0; index < IP_HEADERS_TO_TRY.length && !found; index++) {
            ip = request.getHeader(IP_HEADERS_TO_TRY[index]);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                if (index == 0) {
                    int spIndex = ip.indexOf(",");
                    if (spIndex > 0) {
                        ip = ip.substring(0, spIndex);
                    }
                }
                found = true;
            }
        }
        if (!found) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "localhost" : ip;
    }

    private static CookieStore configCookieStore(List<Cookie> cookieList) {
        if (!ListUtil.isEmpty(cookieList)) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Cookie cookie : cookieList) {
                cookieStore.addCookie(cookie);
            }
            return cookieStore;
        }
        return null;
    }

    private static void configHeader(Map<String, String> requestHeaders, HttpRequestBase httpMethod) {
        if (MapUtil.isNotEmpty(requestHeaders)) {
            requestHeaders.forEach((k, v) -> httpMethod.addHeader(k, v));
        }
    }

    private static List<NameValuePair> assemblePair(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value != null) {
                pairs.add(new BasicNameValuePair(entry.getKey(), value));
            }
        }
        return pairs;
    }

    private static <T> HttpResponse<T> executeHttpRequest(String url, Map<String, String> params, Map<String, String> requestHeader, List<Cookie> cookieList, Class<T> clazz, Boolean isSnakeCase, Integer socketTimeout, HttpRequestBase httpRequestBase, String method) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
                .build();
        HttpResponse<T> stoHttpResponse = new HttpResponse<>();
        try {
            if (socketTimeout != null && socketTimeout != 0) {
                httpRequestBase.setConfig(RequestConfig.custom()
                        .setSocketTimeout(socketTimeout)
                        .setConnectTimeout(TIMEOUT)
                        .setConnectionRequestTimeout(TIMEOUT)
                        .build());
            } else {
                httpRequestBase.setConfig(requestConfig);
            }

            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(configCookieStore(cookieList));

            configHeader(requestHeader, httpRequestBase);

            response = httpClient.execute(httpRequestBase, httpClientContext);

            int statusCode = response.getStatusLine().getStatusCode();
            stoHttpResponse.setCode(statusCode);

            Header[] headers = response.getAllHeaders();
            Map<String, String> headerMap = new HashMap<>(2);
            if (headers != null && headers.length > 0) {
                Arrays.stream(headers).forEach(header -> {
                    HeaderElement[] elements = header.getElements();
                    if (elements != null && elements.length > 0) {
                        Arrays.stream(elements).forEach(element -> {
                            NameValuePair[] nameValuePairs = element.getParameters();
                            Arrays.stream(nameValuePairs).forEach(
                                    pair -> headerMap.put(pair.getName(), pair.getValue()));
                        });
                    }
                });
            }
            stoHttpResponse.setHeader(headerMap);

            stoHttpResponse.setCookieList(httpClientContext.getCookieStore().getCookies());

            String msg = EntityUtils.toString(response.getEntity(), "utf-8");
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = msg;
            }
            stoHttpResponse.setEntity(result);
            EntityUtils.consume(entity);
            logger.info(
                    "HttpClient : " + method + " request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                            + " , request header is: " + JsonUtil.toJson(requestHeader) + " request response: " + result);

            if (StringUtil.isNotEmpty(stoHttpResponse.getEntity()) && clazz != null) {
                Result Result = isSnakeCase ? JsonUtil.fromJsonSnakeCase(stoHttpResponse.getEntity(),
                        Result.class) : JsonUtil.fromJson(stoHttpResponse.getEntity(), Result.class);
                @SuppressWarnings("unchecked")
                Map<String, Object> stoData = (Map<String, Object>) Result.getData();
                stoHttpResponse.setResult(
                        isSnakeCase ? JsonUtil.fromJsonSnakeCase(JsonUtil.toJson(stoData), clazz)
                                : JsonUtil.fromJson(JsonUtil.toJson(stoData), clazz));
            }
            return stoHttpResponse;
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.error("close " + method + " failed, request url: " + url + " , request params is: " + JsonUtil.toJson(params)
                        + " , request header is: " + JsonUtil.toJson(requestHeader) + " , error is: " + e.getLocalizedMessage());
            }
        }
    }

}
