import java.text.DecimalFormat;

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

        DecimalFormat df = new DecimalFormat("00000");
        System.out.println(df.format(1));
    }
}
