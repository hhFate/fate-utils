package cn.reinforce.plugin.util;

import cn.reinforce.plugin.util.entity.HttpResult;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的一些工具
 *
 * @author Fate
 */
public class Tools {

    private static final Logger LOG = Logger.getLogger(Tools.class);

    private static final String[] numberUpper = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] number = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] unit = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};

    private Tools() {
        super();
    }

    /**
     * int型强转时设置默认值
     *
     * @param src
     * @param def
     * @return
     */
    public static int parseInt(String src, int def) {
        if (StringUtils.isEmpty(src)) {
            return def;
        } else {
            return Integer.parseInt(src);
        }
    }

    public static int parseInt(String src) {
        return parseInt(src, 0);
    }

    /**
     * long型强转时设置默认值
     *
     * @param src
     * @param def
     * @return
     */
    public static long parseLong(String src, long def) {
        if (StringUtils.isEmpty(src)) {
            return def;
        } else {
            return Long.parseLong(src);
        }
    }

    public static long parseLong(String src) {
        return parseLong(src, 0L);
    }

    public static double parseDouble(String src, double def) {
        if (StringUtils.isEmpty(src)) {
            return def;
        } else {
            return Double.parseDouble(src);
        }
    }

    public static double parseDouble(String src) {
        return parseDouble(src, 0);
    }

    /**
     * 数组转列表,去掉空字符串
     *
     * @param src
     * @return
     */
    public static List<String> asList(String[] src) {
        List<String> list = new ArrayList<>();
        for (String s : src) {
            if (!StringUtils.isEmpty(s)) {
                list.add(s.trim());
            }
        }
        return list;
    }

    public static List<Integer> asIntegerList(String[] src) {
        List<Integer> list = new ArrayList<>();
        for (String s : src) {
            if (s != null && !"".equals(s)) {
                list.add(Integer.parseInt(s.trim()));
            }
        }
        return list;
    }

    /**
     * long类型的文件大小转换成字符串
     *
     * @param size
     * @return
     */
    public static String sizeConvert(long size) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (size < 1024)
            return size + "字节";
        else if (size < 1024 * 1024)
            return df.format(size * 1.0 / 1024) + "K";
        else if (size < 1024 * 1024 * 1024)
            return df.format(size * 1.0 / 1024 / 1024) + "M";
        else
            return df.format(size * 1.0 / 1024 / 1024 / 1024) + "G";
    }

    /**
     * 简单的数字转汉字，不带单位
     * etc:1-->壹,345-->叁肆伍
     *
     * @param src 数字
     * @return
     */
    public static String simpleNumberToHans(long src) {
        StringBuilder builder = new StringBuilder();
        while (src > 0) {
            int t = (int) (src % 10);
            builder.append(number[t]);
            src = src / 10;
        }
        return builder.toString();
    }

    /**
     * 判断请求是否来自手机
     *
     * @param request
     * @return
     */
    public static boolean isMoblie(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile"};
        if (request.getHeader("User-Agent") != null) {
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }


    /**
     * 主动提交网址到百度
     *
     * @param urls 要提交的url
     * @param site 网站地址
     * @param token 百度的token
     * @param original 是否原创
     */
    public static void baiduUrls(String urls, String site, String action, String token, boolean original) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://data.zz.baidu.com/" + action + "?site=" + site + "&token=" + token + (original ? "&type=original" : "");
        HttpPost post = new HttpPost(url);

        StringEntity entity = null;
        try {
            entity = new StringEntity(urls);
        } catch (UnsupportedEncodingException e) {
            LOG.error("编码错误", e);
        }
        post.setEntity(entity);
        post.addHeader("Content-Type", "text/html;charset=UTF-8");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(post);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        HttpEntity responseEntity = null;
        if(response!=null){
            responseEntity = response.getEntity();
        }

        if (responseEntity != null) {
            String submitResult = null;
            try {
                submitResult = EntityUtils.toString(responseEntity, "UTF-8");
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
            HttpResult result = new HttpResult();
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setResult(submitResult);
        }

    }


}
