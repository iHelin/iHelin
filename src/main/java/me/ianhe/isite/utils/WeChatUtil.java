package me.ianhe.isite.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 微信常用工具类
 *
 * @author linhe2
 * @since 16/11/4
 */
public class WeChatUtil {

    private static final HttpClientBuilder HTTP_CLIENT_BUILDER = HttpClientBuilder.create();

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatUtil.class);

    /**
     * get请求，返回String
     *
     * @author iHelin
     * @since 2017-05-10 16:54
     */
    public static String doGetStr(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HTTP_CLIENT_BUILDER.build();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            LOGGER.warn("error while doGet url:{}", url, e);
            return null;
        } finally {
            httpGet.releaseConnection();
        }
    }

    /**
     * post请求
     *
     * @author iHelin
     * @since 2017-05-10 16:54
     */
    public static String doPostStr(String url, String outStr) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HTTP_CLIENT_BUILDER.build();
        HttpEntity reqEntity = new StringEntity(outStr, ContentType.APPLICATION_JSON);
        httpPost.setEntity(reqEntity);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            return EntityUtils.toString(respEntity, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            LOGGER.warn("error while doPost url:{}", url, e);
            return null;
        } finally {
            httpPost.releaseConnection();
        }
    }

    private WeChatUtil() {
        //工具类不允许实例化
    }

}
