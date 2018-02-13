package cn.reinforce.plugin.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 友链工具
 *
 * @author 幻幻Fate
 * @create 2016-09-06
 * @since 1.0.0
 */
public class FriendLinkUtil {

    private static final Logger LOG = LoggerFactory.getLogger(FriendLinkUtil.class.getName());

    private FriendLinkUtil() {
        super();
    }

    /**
     * 检测网站的友链是否互链
     *
     * @param url
     * @return
     */
    public static boolean checkLink(String website, String url) {
        try {
            Document target = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").get();
            return target.toString().contains(website);
        } catch (IOException e) {
            LOG.error("网站解析错误", e);
        }
        return false;
    }
}
