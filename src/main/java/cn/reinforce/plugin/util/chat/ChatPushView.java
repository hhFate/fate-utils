package cn.reinforce.plugin.util.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 推送
 * 
 * @author Fate
 *
 */
public class ChatPushView {

	/**
	 * 接收人的手机号
	 */
	private List<String> mobile = new ArrayList<>();
	
	/**
	 * 推送通知的内容
	 */
	@Expose
	private String content;

	/**
	 * 推送时间
	 */
	@Expose
	private Date pushTime = new Date();

	/**
	 * 操作人姓名
	 */
	@Expose
	private String username;
	
	/**
	 * 操作人头像
	 */
	@Expose
	private String headIcon;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public List<String> getMobile() {
		return mobile;
	}

	public void setMobile(List<String> mobile) {
		this.mobile = mobile;
	}


}
