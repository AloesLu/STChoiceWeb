package com.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * stringutil
 * 
 * @author wt
 *
 */
public class StringUtil {
	public static boolean notNull(String str) {

		if (str == null)
			return false;
		if (str.equals(""))
			return false;
		if (str.equalsIgnoreCase("null"))
			return false;
		return true;
	}

	/**
	 * 字符转int Description: Date:2012-11-3
	 * 
	 * @author wm
	 * @param @param str
	 * @param @return
	 * @return long
	 */
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 字符long Description: Date:2012-11-3
	 * 
	 * @author wm
	 * @param @param str
	 * @param @return
	 * @return long
	 */
	public static long parseLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return 0l;
		}
	}

	/**
	 * 字符转int
	 * 
	 * @param request
	 * @param paramKey
	 * @return
	 */
	public static Integer parseInteger(HttpServletRequest request,
			String paramKey) {
		try {
			return Integer.valueOf(request.getParameter(paramKey));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * isEmpty:(判断对象是否为空)
	 * 
	 * @param obj
	 * @return
	 * @throws
	 * @since Ver 1.1
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj.equals(""))
			return true;
		if (obj.toString().equalsIgnoreCase("null"))
			return true;
		return false;
	}

	/**
	 * 
	 * 当obj="0",0,"null","false"时也判为空
	 * 
	 * @param obj
	 * @return
	 * @throws
	 * @since Ver 1.1
	 */
	public static boolean isEmpty2(Object obj) {
		if (isEmpty(obj))
			return true;
		if (obj.equals(0))
			return true;
		if (obj.equals("0"))
			return true;
		if (obj.equals("null"))
			return true;
		if (obj.equals("false"))
			return true;
		if (obj.equals("nil"))
			return true;
		if (obj.equals("undefined"))
			return true;
		return false;
	}

	public static String taskEscapeChar(String str) {
		if (str == null)
			return "";
		str = str.replace("\\", "\\\\");
		str = str.replaceAll("\r", "\\\\r");
		str = str.replaceAll("\n", "\\\\n");
		str = str.replaceAll("\"", "\\\\\"");
		str = str.replaceAll("\t", "\\\t");
		return str;
	}

	public static String[] getDomainidByDeviceid(String deviceid) {
		String str[];
		int i = deviceid.indexOf("@");
		if (i == 0 || i == -1 || i == deviceid.length()) {
			str = new String[1];
			str[0] = deviceid;
		} else {
			str = new String[2];
			str = deviceid.split("@");
		}
		return str;

	}

	/**
	 * 将身份证长度转化成18位
	 * 
	 * @param idCard
	 * @return
	 */
	public static String convertIdTo18(String idCard) {
		if (idCard == null)
			return "";
		if (idCard.length() != 15)
			return idCard;
		int iS = 0;
		// 加权因子常数
		int[] iW = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
				4, 2 };
		// 校验码常数
		String LastCode = "10X98765432";
		// 新身份证号
		String perIDNew;
		perIDNew = idCard.substring(0, 6);
		// 填在第6位及第7位上填上‘1’，‘9’两个数字
		perIDNew += "19";
		perIDNew += idCard.substring(6);
		// 进行加权求和
		for (int i = 0; i < 17; i++) {
			iS += Integer.parseInt(perIDNew.substring(i, i + 1)) * iW[i];
		}
		// 取模运算，得到模值
		int iY = iS % 11;
		// 从LastCode中取得以模为索引号的值，加到身份证的最后一位，即为新身份证号。
		perIDNew += LastCode.substring(iY, iY + 1);
		return perIDNew;
	}

	/**
	 * add by haven.qu 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int getStringLength(String value) {
		int valueLength = 0;
		if (value == null)
			return valueLength;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * add by haven.qu 获取字符串的长度，如果有中文，则每个中文字符计为3位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int getStringLengthByUtf8(String value) {
		int valueLength = 0;
		if (value == null)
			return valueLength;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为3 */
				valueLength += 3;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 
	 * converStringArrayToLongArray:(将String型数据转换成Long型数据)
	 * 
	 * @param stringArray
	 * @return
	 * @throws
	 * @since Ver 1.1
	 */
	public static Long[] converStringArrayToLongArray(String[] stringArray) {
		if (stringArray == null)
			return null;
		Long[] longArray = new Long[stringArray.length];
		try {
			for (int i = 0; i < stringArray.length; i++) {
				longArray[i] = Long.valueOf(stringArray[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return longArray;
	}

	/**
	 * 
	 * convString:(数组转字符串 bufferFlg 分隔 )
	 * 
	 * @param array
	 * @param bufferFlg
	 * @return
	 * @throws
	 * @since Ver 1.1
	 */
	public static String convString(Object obj, String bufferFlg) {
		String text = "";
		StringBuffer temp = new StringBuffer();
		int length = java.lang.reflect.Array.getLength(obj);
		for (int i = 0; i < length; i++) {
			if (java.lang.reflect.Array.get(obj, i) != null) {
				temp.append(java.lang.reflect.Array.get(obj, i)).append(
						bufferFlg);
			}
		}
		text = temp.toString();
		if (text.endsWith(bufferFlg)) {
			text = text.substring(0, text.length() - 1);
		}
		return text;
	}

	// 判断字符串是否含有中文
	public static Boolean isChCode(String str) {
		Boolean flag = false;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find())
			flag = true;
		else
			flag = false;
		return flag;
	}

	/**
	 * 让HTML格式内容转义，使页面正常显示
	 */
	public static String convertHTML(String obj) {
		obj = obj.replaceAll("&", "&amp;").replaceAll("\"", "&quot;")
				.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		return obj;
	}

	/**
	 * 
	 * specialCharacterTransferred:(查询条件% _ '处理)
	 * 
	 * @param searchString
	 * @return
	 * @throws
	 * @since Ver 1.1
	 */
	public static String specialCharacterTransferred(String searchString) {
		if (!StringUtil.isEmpty(searchString)) {
			searchString = searchString.replace("\\", "\\\\");
			searchString = searchString.replace("%", "\\%");
			searchString = searchString.replace("_", "\\_");
			searchString = searchString.replaceAll("'", "''");
			searchString = searchString.trim();
		}
		return searchString;
	}

	/**
	 * 
	 * convString:(数组转字符串 bufferFlg 分隔 )
	 * 
	 * @param array
	 * @param bufferFlg
	 * @return
	 * @throws
	 * @since Ver 1.1
	 */
	public static String[] convArray(String str, String bufferFlg) {
		if (str == null || str.length() < 1) {
			return null;
		}
		String[] stringArr = str.split(bufferFlg);

		return stringArr;
	}

	/**
	 * 处理照片图片到list
	 * 
	 * @param pictures
	 * @return
	 */
	public static List<String> dealPicStr(String pictures) {
		List<String> picList = new ArrayList<String>();
		if (pictures != null && pictures.length() > 0) {
			String[] pics = pictures.split(",");
			for (int i = 0; i < pics.length; i++) {
				picList.add(pics[i]);
			}
		}
		return picList;
	}

	public static String dealPicList(String picList) {
		StringBuffer pictures = new StringBuffer();
		if (isEmpty(picList)) {
			return pictures.toString();
		}
		JSONArray picArray = JSON.parseArray(picList);
		if (picArray != null) {
			for (int i = 0; i < picArray.size(); i++) {
				if (pictures.length() == 0) {
					pictures.append(picArray.get(i).toString());
				} else {
					pictures.append(",").append(picArray.get(i).toString());
				}
			}
		}
		return pictures.toString();
	}

	/**
	 * 过滤html为村文本
	 * 
	 * @param html
	 * @return
	 */
	public static String removeHtmlStr(String html) {
		if (isEmpty(html)) {
			return "";
		}
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(html);
		regEx_html = m_html.replaceAll("");
		return regEx_html.replaceAll("&nbsp;", " ").replaceAll(
				"\t|\n|\r|\b|\f", ""); // 过滤html标签
	}

	/**
	 * 让HTML格式内容转义，使页面正常显示
	 */
	public static String nvconvertHTML(String obj) {
		obj = obj.replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
				.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		return obj;
	}

	/**
	 * 返回对象字符串，对于出错情况返回空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		try {
			return obj == null ? "" : obj.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判空操作
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * 用户头像转换
	 * 
	 * @param avatar
	 * @param pictures
	 * @return
	 */
	public static String userPictuesToNewPic(String avatar, String pictures) {
		String[] pics = pictures.split(",");
		String pic = avatar;
		// 被修改的图片是否在列表中存在
		boolean flag = false;
		if (pictures.contains(avatar)) {
			flag = true;
		}
		for (String picture : pics) {
			if (flag && avatar.equals(picture)) {
				continue;
			}
			pic += "," + picture;
		}
		return pic;
	}

	/**
	 * 讲set转换为String数组
	 * 
	 * @param set
	 * @return
	 */
	public static String[] getStringKeys(List<String> set) {
		String[] strs = new String[set.size()];
		int i = 0;
		for (String action : set) {
			strs[i] = action;
			i++;
		}
		return strs;
	}

	/**
	 * 讲set转换为String数组
	 * 
	 * @param set
	 * @return
	 */
	public static String[] getStringKeys(Set<String> set) {
		String[] strs = new String[set.size()];
		int i = 0;
		for (String action : set) {
			strs[i] = action;
			i++;
		}
		return strs;
	}

	/**
	 * 去除手机号码无效内容
	 * 
	 * @param phone
	 * @return
	 */
	public static String replasePhoneStr(String phone) {
		if (!isEmpty(phone)) {
			if (phone.contains(" ")) {
				phone = phone.replaceAll(" ", "");
			}
			if (phone.startsWith("86")) {
				phone = phone.replaceFirst("86", "");
			} else if (phone.startsWith("+86")) {
				phone = phone.replaceFirst("\\+86", "");
			}
			if (phone.contains("-")) {
				phone = phone.replaceAll("-", "");
			}
		}
		return phone;
	}

	/**
	 * 组装redis Key
	 * 
	 * @param redisStr
	 * @param key
	 * @return
	 */
	public static String getRedisKey(String redisStr, String key) {
		if (!StringUtil.isEmpty(key) && key.contains(":")) {
			return key;
		} else {
			return redisStr + key;
		}
	}

	/**
	 * 修改手机为16位
	 * 
	 * @param phone
	 * @return
	 */
	public static String chanagRegisterPhone(String phone) {
		while (phone.length() < 16) {
			phone += "0" + phone;
		}
		return phone;
	}

	/**
	 * 处理地区查询去除省市区县尾端字符
	 * 
	 * @param content
	 * @return
	 */
	public static String changeLocationContent(String content) {
		String con = content.substring(content.length() - 1, content.length());
		if (con.equals("省") || con.equals("市") || con.equals("区")
				|| con.equals("县")) {
			return content.substring(0, content.length() - 1);
		}
		return content;

	}

	/**
	 * 判断字符串数组是否包含某个元素
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean isContains(String[] arr, String e) {
		if(e == null){
			return false;
		}
		for(String ele:arr){
			if(e.equals(ele)){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// String ss =
		// "&lt;span style=&quot;color:#333333;font-family:Arial, 'Microsoft YaHei';font-size:12px;line-height:23px;background-color:#F2F2F5;&quot;&gt;切，单片机，会做&lt;/span&gt;";
		// System.out.println(removeHtmlStr(ss));
	}
}
