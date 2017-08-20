package cn.reinforce.plugin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class SignatureUtil {

	private static final String ENCODING = "UTF-8";
	
	private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	private SignatureUtil() {
	}

	public static String percentEncode(String value) throws UnsupportedEncodingException {
        return value != null ? URLEncoder.encode(value, ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
    }
	
	public static String formatIso8601Date(Date date) {
	    SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
	    df.setTimeZone(new SimpleTimeZone(0, "GMT"));
	    return df.format(date);
	}
}
