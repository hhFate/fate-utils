package cn.reinforce.plugin.util;

/**
 * @author 幻幻Fate
 * @create 2018/1/26
 * @since
 */
public class TemperatureUtil {

    private TemperatureUtil() {
        super();
    }

    public static Double degree2Fahrenheit(Double degree) {
        if (degree == null) {
            return null;
        }
        return degree * 1.8 + 32;
    }

    public static Double fahrenheit2Degree(Double fahrenheit) {
        if (fahrenheit == null) {
            return null;
        }
        return (fahrenheit - 32) / 1.8;
    }
}
