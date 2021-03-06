import cn.reinforce.plugin.util.HttpClientUtil;
import cn.reinforce.plugin.util.MD5;
import cn.reinforce.plugin.util.TokenUtil;
import cn.reinforce.plugin.util.Tools;
import cn.reinforce.plugin.util.entity.HttpResult;
import cn.reinforce.plugin.util.juhe.JHUtil;
import cn.reinforce.plugin.util.juhe.entity.JuheResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 幻幻Fate
 * @create 2017/9/21
 * @since
 */
public class Test {

    public static void main(String[] args) {

//        List<NameValuePair> data = new ArrayList<>();
//        data.add(new BasicNameValuePair("content", "大大说的"));
//        data.add(new BasicNameValuePair("appKey", "P36CldLCizUQbbc"));
//        data.add(new BasicNameValuePair("sign", "75f0b3a48e9a0dec901cf182a48db5d0"));
//        data.add(new BasicNameValuePair("timestamp", "1507603621000"));
//        HttpResult result = HttpClientUtil.post("http://192.168.56.1:772/test", data);
//        System.out.println(result);
//        Map<String, String> parameters = new LinkedHashMap<>();
//        parameters.put("file_path", "E:\\各种收藏\\日语日常用语总结.pdf");
//        parameters.put("appKey", "P36CldLCizUQbbc");
//        parameters.put("sign", "75f0b3a48e9a0dec901cf182a48db5d0");
//        parameters.put("timestamp", "1507603621000");
//        parameters.put("ossBucket", "fate-fms");
//        parameters.put("ossUrl", "");
//        parameters.put("folder", "");
//        parameters.put("filename", "日语日常用语总结.pdf");

//        List<Cookie> cookies = new ArrayList<>();
//        cookies.add(new Cookie("UM_distinctid", "15e695e7d47d7-0fed0d503119c7-5d4e211f-1fa400-15e695e7d48f7"));
//        cookies.add(new Cookie("CNZZDATA30088308", "cnzz_eid%3D1084275154-1505013811-%26ntime%3D1508667004"));
//        cookies.add(new Cookie("fly-layui", "s%3AmAXHpRLIhEMcEBvdsWjt3P_K7aFNC3T2.qhl%2B4uWOUM37qLagw5V%2FDdFrrVgRZU4LRRxe7eNOPQ4"));
//
//        System.out.println(HttpClientUtil.post("http://fly.layui.com/sign/status", data, cookies));
//        System.out.println(HttpClientUtil.post("http://fly.layui.com/sign/in", data, cookies));

//        System.out.println(Tools.parseDouble("0.01"));

//        System.out.println(TokenUtil.getRandomString(32, 2));

//        LOG.info("-----------Fly社区签到-----------");

        int delay = 0;

        try {
            Thread.sleep(delay * 60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<NameValuePair> data = new ArrayList<>();
        List<Cookie> cookies = new ArrayList<>();
//        cookies.add(new Cookie("UM_distinctid", "15e5f549f079e6-0d1722e589bcb-8383667-1aeaa0-15e5f549f0883d"));
//        cookies.add(new Cookie("CNZZDATA30088308", "cnzz_eid%3D1084275154-1505013811-%26ntime%3D1508667004"));
        cookies.add(new Cookie("fly-layui", "s%3AoNIIKp0upVKOPhKnTut7bLnRzB_e2Q3P.wBppbbFfThxEYfx0EGc2x7c4xCPVvOu1LU6FIFy5Fb0"));

        HttpResult result = HttpClientUtil.post("http://fly.layui.com/sign/status", data, cookies);

        System.out.println(result);

        JSONObject r = new JSONObject(result.getResult());

        if(r.has("msg")){
            //发送通知短信
            JuheResponse response = JHUtil.sendCommonSms("17306187352", null, "03abf4264d5bc4b5bf7f927857b85b2f", 56310);
            System.out.println(response.getReason());
            return;
        }

        JSONObject d = r.getJSONObject("data");
        boolean signed = d.getBoolean("signed");
        if(!signed){
            String token = d.getString("token");
            data.add(new BasicNameValuePair("token", StringUtils.isEmpty(token)?"1":token));
            try {
                Thread.sleep(delay * 3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(HttpClientUtil.post("http://fly.layui.com/sign/in", data, cookies));
        }

    }
}
