package cn.reinforce.plugin.util.juhe;

import cn.reinforce.plugin.util.GsonUtil;
import cn.reinforce.plugin.util.HttpClientUtil;
import cn.reinforce.plugin.util.juhe.entity.IP;
import cn.reinforce.plugin.util.juhe.entity.JuheResponse;
import cn.reinforce.plugin.util.juhe.entity.Mobile;
import cn.reinforce.plugin.util.juhe.entity.Sms;
import cn.reinforce.plugin.util.juhe.entity.Weather;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合数据工具类
 *
 * @author 幻幻Fate
 * @create 2016-09-06
 * @since 1.0.0
 */
public class JHUtil {

    private JHUtil() {
        super();
    }

    /**
     * 通用短信发送接口
     *
     * @param mobile     手机号
     * @param tplValue   模版
     * @param key        聚合key
     * @param templeteId 模版id
     * @return
     */
    public static JuheResponse sendCommonSms(String mobile, String tplValue, String key, int templeteId) {
        List<NameValuePair> pair = new ArrayList<>();
        pair.add(new BasicNameValuePair("mobile", mobile));
        pair.add(new BasicNameValuePair("tpl_id", Integer.toString(templeteId)));
        pair.add(new BasicNameValuePair("tpl_value", tplValue));
        pair.add(new BasicNameValuePair("key", key));

        String result = HttpClientUtil.post("http://v.juhe.cn/sms/send", pair, "").getResult();
        Gson gson = new Gson();
        JuheResponse response = gson.fromJson(result, JuheResponse.class);

        Sms sms = gson.fromJson(gson.toJson(response.getResult()), Sms.class);

        response.setSms(sms);

        return response;
    }

    /**
     * 短信发送
     *
     * @param mobile     手机号
     * @param code       验证码
     * @param timeout    超时时间，分钟
     * @param templeteId 短信模版id
     * @return 错误码
     */
    public static JuheResponse sendSms(String mobile, String code, int timeout, String key, int templeteId) {
        return sendCommonSms(mobile, "#code#=" + code + "&#hour#=" + timeout, key, templeteId);
    }

    /**
     * 评论提醒通知
     *
     * @param mobile     手机号
     * @param title
     * @param content
     * @param key
     * @param templeteId 短信模版id
     * @return 错误码
     */
    public static JuheResponse sendCommentSms(String mobile, String title, String content, String key, int templeteId) {
        return sendCommonSms(mobile, "#title#=" + title + "&#content#=" + content, key, templeteId);
    }

    /**
     * 友链申请提醒
     *
     * @param mobile
     * @param site
     * @param key
     * @param templeteId
     * @return
     */
    public static JuheResponse sendFriendLinkSms(String mobile, String site, String key, int templeteId) {
        return sendCommonSms(mobile, "#site#=" + site, key, templeteId);
    }


    /**
     * 全国天气查询
     *
     * @param ip
     * @return
     * @throws JSONException
     */
    public static Weather weather(String ip, String key) {
        List<NameValuePair> pair = new ArrayList<>();
        pair.add(new BasicNameValuePair("ip", ip));
        pair.add(new BasicNameValuePair("key", key));
        String result = HttpClientUtil.post("http://v.juhe.cn/weather/ip", pair, "").getResult();
        Gson gson = new Gson();
        JuheResponse response = gson.fromJson(result, JuheResponse.class);

        Weather weather = gson.fromJson(gson.toJson(response.getResult()), Weather.class);

        response.setWeather(weather);
        return weather;
    }

    /**
     * 天气查询，weather.com
     *
     * @param ip
     * @return
     * @throws JSONException
     */
//    public static Map<String, Object> weather2(String ip, String key) {
//        Map<String, Object> map = new HashMap<>();
//
//        String city = ip(ip).getIp().getArea();
//        city = "苏州市";
//        try {
//            city = URLEncoder.encode(city, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String result = HttpClientUtil.get("http://op.juhe.cn/onebox/weather/query?cityname=" + city + "&key="+key).getResult();
//        System.out.println(result);
//        Gson gson = GsonUtil.getGson();
//        JuheResponse response = gson.fromJson(result, JuheResponse.class);
//
//        System.out.println(response.getResult());
//
//        return map;
//    }
    public static JuheResponse ip(String ip, String key) {
        String result = HttpClientUtil.get("http://apis.juhe.cn/ip/ip2addr?ip=" + ip + "&key=" + key).getResult();
        Gson gson = GsonUtil.getGson();
        JuheResponse response = gson.fromJson(result, JuheResponse.class);

        IP ip1 = gson.fromJson(gson.toJson(response.getResult()), IP.class);

        response.setIp(ip1);
        return response;
    }

    /**
     * 获取手机号的运营商
     *
     * @param mobile
     * @return
     */
    public static JuheResponse getMobile(String mobile, String key) {
        String result = HttpClientUtil.get("http://apis.juhe.cn/mobile/get?phone=" + mobile + "&key=" + key).getResult();
        Gson gson = GsonUtil.getGson();
        JuheResponse response = gson.fromJson(result, JuheResponse.class);
        Mobile m = gson.fromJson(gson.toJson(response.getResult()), Mobile.class);

        response.setMobile(m);
        return response;
    }

}
