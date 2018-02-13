package cn.reinforce.plugin.util;

import cn.reinforce.plugin.util.entity.HttpResult;
import org.apache.http.entity.StringEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * @author 幻幻Fate
 * @create 2017/11/13
 * @since
 */
public class SEOUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SEOUtil.class.getName());

    private SEOUtil() {
        super();
    }

    /**
     * 主动提交网址到百度
     *
     * @param urls  要提交的url
     * @param site  网站地址
     * @param token 百度的token
     */
    public static void baiduUrls(String urls, String site, String action, String token) {
        String url = "http://data.zz.baidu.com/" + action + "?site=" + site + "&token=" + token;

        StringEntity entity = null;
        try {
            entity = new StringEntity(urls);
        } catch (UnsupportedEncodingException e) {
            LOG.error("编码错误", e);
        }

        HttpResult result = HttpClientUtil.postByEntity(url, entity);

        System.out.println(result);
    }

    /**
     * 熊掌号API提交
     *
     * @param urls
     * @param appId
     * @param action
     * @param token
     * @param original
     */
    public static void xzh(String urls, String appId, String action, String token, boolean original) {

        String type = null;
        switch (action) {
            case "urls":
                type = "realtime";
                break;
            case "update":
                type = "batch";
                break;
            default:
                break;
        }

        String url = "http://data.zz.baidu.com/urls?appid=" + appId + "&token=" + token + "&type=" + type + (original ? ",original" : "");

        StringEntity entity = null;
        try {
            entity = new StringEntity(urls);
        } catch (UnsupportedEncodingException e) {
            LOG.error("编码错误", e);
        }

        HttpResult result = HttpClientUtil.postByEntity(url, entity);

        System.out.println(result);
    }

}
