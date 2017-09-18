package cn.reinforce.plugin.util.entity;

/**
 * @author 幻幻Fate
 * @create 2017/9/14
 * @since
 */
public class Coordinate {
    private double lat;
    private double lng;

    public Coordinate(double lng, double lat) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
