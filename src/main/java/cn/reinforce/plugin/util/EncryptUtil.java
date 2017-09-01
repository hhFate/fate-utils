package cn.reinforce.plugin.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 各种加密相关
 * @author 幻幻Fate
 * @create
 * @since
 */
public class EncryptUtil {

	private static final Logger LOG = Logger.getLogger(EncryptUtil.class);

	private static final String HMAC_SHA1 = "HmacSHA1";

	private EncryptUtil() {
	}

	// md5加密
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	// sha加密
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	// 对密码加密
	public static String pwd(long timestamp, String pwd) {
		long time = timestamp / 1000;
		return EncryptUtil.md5(pwd + time);
	}

	/**
	 * 用户名加盐的方式
	 * @param username
	 * @param pwd
	 * @param salt
	 * @return
	 */
	public static String pwd2(String username, String pwd, String salt) {
		return EncryptUtil.md5(username + pwd + salt);
	}


	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (StringUtils.isEmpty(algorithmName)) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			return hex(s);
		} catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
			LOG.error(e);
		}
		return encryptText;
	}

	// 返回十六进制字符串
	private static String hex(byte[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	public static String getSignature(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
		SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data);
		return DigestUtils.md5Hex(rawHmac);
	}
}
