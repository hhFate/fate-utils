package cn.reinforce.plugin.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一堆正则表达式
 *
 * @author 幻幻Fate
 * @create 2017-01-04
 * @since 1.0.1
 */
public class RegularUtil {

    private RegularUtil() {
        super();
    }

    /**
     * 判断手机号的格式
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Pattern pattern = Pattern.compile("^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$|17[0-9]{9}$");
        Matcher matcher = pattern.matcher(mobile);
        return !matcher.find();
    }

    /**
     * 是否是座机
     * @param tel
     * @return
     */
    public static boolean isTel(String tel){
        if(StringUtils.isEmpty(tel)){
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]{3,4}-[0-9]{8}$");
        Matcher matcher = pattern.matcher(tel);
        return !matcher.find();
    }

    /**
     * 检查邮箱格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if(StringUtils.isEmpty(email)){
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher matcher = pattern.matcher(email);
        return !matcher.find();
    }
}
