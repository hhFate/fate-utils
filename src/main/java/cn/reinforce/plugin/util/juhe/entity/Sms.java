package cn.reinforce.plugin.util.juhe.entity;

/**
 * 对应短信API服务
 *
 * @author Fate
 */
public class Sms {

    /**
     * 短信的id
     */
    private String sid;

    /**
     * 发送数量
     */
    private int count;

    /**
     * 扣款条数
     */
    private int fee;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Result{" +
                "sid='" + sid + '\'' +
                ", count=" + count +
                ", fee=" + fee +
                '}';
    }

}
