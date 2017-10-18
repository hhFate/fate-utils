import cn.reinforce.plugin.util.HttpClientUtil;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 幻幻Fate
 * @create 2017/9/21
 * @since
 */
public class Test {

    public static void main(String[] args) {

//        List<NameValuePair> data = new ArrayList<>();
//        data.add(new BasicNameValuePair("mobile", "17306187352"));
//        data.add(new BasicNameValuePair("appKey", "swuO3NXLHQPCnfB"));
//        data.add(new BasicNameValuePair("sign", "4a031c00b43f81a048840e3cfdff74ec"));
//        data.add(new BasicNameValuePair("timestamp", "1507603621000"));
//        HttpResult result = HttpClientUtil.post("http://rc-juhe.reinforce.cn/smsCode", data);
//        System.out.println(result);
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("file_path", "F:\\麦卡软件\\日志填写细则.xls");
        parameters.put("appKey", "P36CldLCizUQbbc");
        parameters.put("sign", "75f0b3a48e9a0dec901cf182a48db5d0");
        parameters.put("timestamp", "1507603621000");
        parameters.put("ossBucket", "fate-fms");
        parameters.put("ossUrl", "");
        parameters.put("folder", "");
        parameters.put("filename", "日志填写细则.xls");
        System.out.println(HttpClientUtil.multipartRequest("http://192.168.1.78:772/upload", parameters));
    }
}
