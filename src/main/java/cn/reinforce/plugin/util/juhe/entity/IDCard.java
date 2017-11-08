package cn.reinforce.plugin.util.juhe.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author 幻幻Fate
 * @create 2017/11/8
 * @since
 */
public class IDCard {

    @Expose
    @SerializedName("realname")
    private String realName;

    @Expose
    @SerializedName("idcard")
    private String idCard;

    @Expose
    private int res;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
