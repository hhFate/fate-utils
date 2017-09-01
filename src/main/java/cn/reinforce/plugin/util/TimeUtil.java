package cn.reinforce.plugin.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具
 *
 * @author 幻幻Fate
 * @create 2016-07-28
 * @since 1.0.0
 */
public class TimeUtil {

    private static final Logger LOG = Logger.getLogger(TimeUtil.class);

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String[] _MMM = new String[]{"Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
    };

    private static final long MS_DAY = 3600L * 24 * 1000;
    private static final long MS_WEEK = MS_DAY * 7;

    public static final int T_1S = 1000;
    public static final int T_1M = 60 * 1000;
    public static final int T_1H = 60 * 60 * 1000;
    public static final int T_1D = 24 * 60 * 60 * 1000;


    private static final String TIME_S_EN = "s";
    private static final String TIME_M_EN = "m";
    private static final String TIME_H_EN = "h";
    private static final String TIME_D_EN = "d";

    private static final String TIME_S_CN = "秒";
    private static final String TIME_M_CN = "分";
    private static final String TIME_H_CN = "时";
    private static final String TIME_D_CN = "天";

    private TimeUtil() {
        super();
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param pattern 默认格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf;
        if (pattern != null) {
            sdf = new SimpleDateFormat(pattern);
        } else {
            sdf = new SimpleDateFormat(PATTERN);
        }
        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, PATTERN);
    }

    /**
     * 快速字符串转日期工具
     *
     * @param date
     * @param pattern 默认格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parseDate(String date, String pattern) {
        try {
            SimpleDateFormat sdf;
            if (pattern != null) {
                sdf = new SimpleDateFormat(pattern);
            } else {
                sdf = new SimpleDateFormat(PATTERN);
            }
            return sdf.parse(date);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public static Date parseDate(String date) {
        return parseDate(date, PATTERN);
    }

    /**
     * 判断一年是否为闰年，如果给定年份小于1全部为 false
     *
     * @param year 年份，比如 2012 就是二零一二年
     * @return 给定年份是否是闰年
     */
    public static boolean leapYear(int year) {
        if (year < 4)
            return false;
        return (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * 判断某年（不包括自己）之前有多少个闰年
     *
     * @param year 年份，比如 2012 就是二零一二年
     * @return 闰年的个数
     */
    public static int countLeapYear(int year) {
        // 因为要计算年份到公元元年（0001年）的年份跨度，所以减去1
        int span = year - 1;
        return (span / 4) - (span / 100) + (span / 400);
    }


    /**
     * 将一个时间格式化成容易被人类阅读的格式
     * <p>
     * <pre>
     * 如果 1 分钟内，打印 Just Now
     * 如果 1 小时内，打印多少分钟
     * 如果 1 天之内，打印多少小时之前
     * 如果是今年之内，打印月份和日期
     * 否则打印月份和年
     * </pre>
     *
     * @param d
     * @return
     */
    public static String formatForRead(Date d) {
        long ms = System.currentTimeMillis() - d.getTime();
        // 如果 1 分钟内，打印 Just Now
        if (ms < (60000)) {
            return "刚刚";
        }
        // 如果 1 小时内，打印多少分钟
        if (ms < (60 * 60000)) {
            return Long.toString(ms / 60000) + "分钟前";
        }

        // 如果 1 天之内，打印多少小时之前
        if (ms < (24 * 3600 * 1000)) {
            return Long.toString(ms / 3600000) + "小时前";
        }

        // 如果一周之内，打印多少天之前
        if (ms < (7 * 24 * 3600 * 1000)) {
            return Long.toString(ms / (24 * 3600000)) + "天前";
        }

        // 如果是今年之内，打印月份和日期
        Calendar c = Calendar.getInstance();
        int thisYear = c.get(Calendar.YEAR);

        c.setTime(d);
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        if (thisYear == yy) {
            int dd = c.get(Calendar.DAY_OF_MONTH);
            return String.format("%s %d", _MMM[mm], dd);
        }

        // 否则打印月份和年
        return String.format("%s %d", _MMM[mm], yy);
    }


    /**
     * 一段时间长度的毫秒数转换为一个时间长度的字符串
     * <p>
     * 1000 -> 1s
     * <p>
     * 120000 - 2m
     *
     * @param mi 毫秒数
     * @return 可以正常识别的文字
     */
    public static String fromMillis(long mi) {
        return fromMillis(mi, TIME_S_EN, TIME_M_EN, TIME_H_EN, TIME_D_EN);
    }

    public static String fromMillisCN(long mi) {
        return fromMillis(mi, TIME_S_CN, TIME_M_CN, TIME_H_CN, TIME_D_CN);
    }

    public static String fromMillis(long mi, String second, String minite, String hour, String day) {
        if (mi < T_1S) {
            return "0" + day;
        }
        if (mi < T_1M) {
            return mi / T_1S + second;
        }
        if (mi < T_1H) {
            long m = mi / T_1M;
            return m + minite + fromMillis(mi - m * T_1M, second, minite, hour, day);
        }
        if (mi < T_1D) {
            long h = mi / T_1H;
            return h + hour + fromMillis(mi - h * T_1H, second, minite, hour, day);
        }
        long d = mi / T_1D;
        return d + day + fromMillis(mi - d * T_1D, second, minite, hour, day);
    }

    /**
     * 粗略的生日计算年龄方法
     *
     * @param birthday
     * @return
     */
    public static int birthdayToAge(Date birthday) {
        long now = System.currentTimeMillis();
        long diff = now - birthday.getTime();
        int age = (int) (diff / (3600000 * 24 * 365L));
        return age;
    }

}
