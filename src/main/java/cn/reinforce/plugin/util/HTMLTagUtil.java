package cn.reinforce.plugin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML处理工具
 * @author 幻幻Fate
 * @create 2016-08-17
 * @since 1.0.0
 */
public class HTMLTagUtil{

	
	
	private HTMLTagUtil() {
	}

	/**
	 * 去掉所有的HTML标签和空白符号
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr) {
		String script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String space = "(\r?\n(\\s*\r?\n)+)";
		String white = "&nbsp;";

		Pattern pScript = Pattern.compile(script, Pattern.CASE_INSENSITIVE);
		Matcher mScript = pScript.matcher(htmlStr);
		htmlStr = mScript.replaceAll(""); // 过滤script标签

		Pattern pStyle = Pattern.compile(style, Pattern.CASE_INSENSITIVE);
		Matcher mStyle = pStyle.matcher(htmlStr);
		htmlStr = mStyle.replaceAll(""); // 过滤style标签

		Pattern pHtml = Pattern.compile(html, Pattern.CASE_INSENSITIVE);
		Matcher mHtml = pHtml.matcher(htmlStr);
		htmlStr = mHtml.replaceAll(""); // 过滤html标签

		Pattern pSpace = Pattern.compile(space, Pattern.CASE_INSENSITIVE);
		Matcher mSpace = pSpace.matcher(htmlStr);
		htmlStr = mSpace.replaceAll("");// 过滤空白数据

		htmlStr = htmlStr.replaceAll(white, "");// 过滤&nbsp;

		return htmlStr.trim(); // 返回文本字符串
	}

}
