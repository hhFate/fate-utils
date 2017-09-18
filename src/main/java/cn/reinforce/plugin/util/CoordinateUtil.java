package cn.reinforce.plugin.util;

import cn.reinforce.plugin.util.entity.Coordinate;

/**
 * 提供了百度坐标（BD09）、国测局坐标（火星坐标，GCJ02）、和WGS84坐标系之间的转换
 *
 * @author 幻幻Fate
 * @create 2017/9/14
 * @since
 */
public class CoordinateUtil {

    public static final double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
    public static final double PI = 3.1415926535897932384626;
    public static final double a = 6378245.0;
    public static final double ee = 0.00669342162296594323;

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     * @param bd_lng
     * @param bd_lat
     * @returns {*[]}
     */
    public static Coordinate bd09ToGcj02(double bd_lng, double bd_lat){
        double x = bd_lng - 0.0065;
        double y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        Coordinate coordinate =new Coordinate(gg_lng, gg_lat);
        return coordinate;
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static Coordinate gcj02ToBd09(double lng, double lat){
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        Coordinate coordinate =new Coordinate(bd_lng, bd_lat);
        return coordinate;
    };

    /**
     * WGS84转GCj02
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static Coordinate wgs84ToGcj02(double lng, double lat){
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double dlng = transformlLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        Coordinate coordinate =new Coordinate(mglng, mglat);
        return coordinate;
    };

    /**
     * GCJ02 转换为 WGS84
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static Coordinate gcj02ToWgs84(double lng, double lat){
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double dlng = transformlLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        Coordinate coordinate =new Coordinate(mglng, mglat);
        return coordinate;
    };


    private static double transformLat(double lng,double lat){
        double ret= -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformlLng(double lng,double lat){
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

//    public static void main(String[] args) {
//
//        Coordinate gps = new Coordinate(121.292355, 30.988315);
//        System.out.println("gps :" + gps);
//        Coordinate gcj = wgs84ToGcj02(gps.getLng(), gps.getLat());
//        System.out.println("gcj :" + gcj);
////        Coordinate star = gcj_To_Gps84(gcj.getLat(), gcj.getLng());
////        System.out.println("star:" + star);
//        Coordinate bd = gcj02ToBd09(gcj.getLng(), gcj.getLat());
//        System.out.println("bd  :" + bd);
//        Coordinate gcj2 = bd09ToGcj02(bd.getLng(), bd.getLat());
//        System.out.println("gcj :" + gcj2);
//    }

    
}
