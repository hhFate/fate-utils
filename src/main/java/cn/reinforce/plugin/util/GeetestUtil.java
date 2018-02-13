package cn.reinforce.plugin.util;

import cn.reinforce.plugin.util.entity.GeetestLib;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * 极验验证的二次验证
 * @author 幻幻Fate
 * @create 2016-08-17
 * @since 1.0.0
 */
public class GeetestUtil {

    private static final Logger LOG = LoggerFactory.getLogger(GeetestUtil.class.getName());

    private GeetestUtil() {
        super();
    }

    public static int validate(String captchaId, String privateKey, HttpServletRequest request){

        GeetestLib gtSdk = new GeetestLib(captchaId, privateKey, false);

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gtServerStatusCode = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        int gtResult;

        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");

        if (gtServerStatusCode == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            LOG.info("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
        }
        return gtResult;
    }
}
