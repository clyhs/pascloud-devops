package com.pascloud.utils;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public final class StrUtil {
	
	private static final Logger logger = Logger.getLogger(StrUtil.class);
	
	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	private static final String US_ASCII = "US-ASCII";

	/** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
	private static final String ISO_8859_1 = "ISO-8859-1";

	/** 8 位 UCS 转换格式 */
	private static final String UTF_8 = "UTF-8";

	/** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
	private static final String UTF_16BE = "UTF-16BE";

	/** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
	private static final String UTF_16LE = "UTF-16LE";

	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	private static final String UTF_16 = "UTF-16";

	/** 中文超大字符集 */
	private static final String GBK = "GBK";

	/***
	 * 
	 * @Title: toGBK
	 * @Description: 将字符编码转换成GBK码
	 * @param @param sStr
	 * @param @return
	 * @param @throws UnsupportedEncodingException 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public final static String toGBK(String sStr)
			throws UnsupportedEncodingException {
		return changeCharset(sStr, GBK);
	}

	/**
	 * 将字符编码转换成UTF-8码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF8(String sStr)
			throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_8);
	}

	/**
	 * 将字符编码转换成US-ASCII码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toASCII(String sStr)
			throws UnsupportedEncodingException {
		return changeCharset(sStr, US_ASCII);
	}

	/**
	 * 将字符编码转换成UTF-16码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF16(String sStr)
			throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_16);
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param sStr
	 *            待转换编码的字符串
	 * @param sNewCharset
	 *            目标编码
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String changeCharset(String sStr, String sNewCharset)
			throws UnsupportedEncodingException {
		// 用默认字符编码解码字符串。
		byte[] aBits = sStr.getBytes();
		// 用新的字符编码生成字符串
		return new String(aBits, sNewCharset);
	}

	/**
	 * 将字符编码转换成ISO-8859-1码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toISO_8859_1(String sStr)
			throws UnsupportedEncodingException {
		return changeCharset(sStr, ISO_8859_1);
	}

	/**
	 * 将字符编码转换成UTF-16BE码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF16BE(String sStr)
			throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_16BE);
	}

	/**
	 * 将字符编码转换成UTF-16LE码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF16LE(String sStr)
			throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_16LE);
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param sStr
	 *            待转换编码的字符串
	 * @param sOldCharset
	 *            原编码
	 * @param sNewCharset
	 *            目标编码
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String changeCharset(String sStr, String sOldCharset,
			String sNewCharset) throws UnsupportedEncodingException {
		// 用旧的字符编码解码字符串。解码可能会出现异常。
		byte[] aBits = sStr.getBytes(sOldCharset);
		// 用新的字符编码生成字符串
		return new String(aBits, sNewCharset);
	}

	/**
	 * 返回正则表达式的结果集
	 * 
	 * @param sStr
	 * @param sPattern
	 * @return List<String>
	 */
	public final static List<String> getRegexResult(String sStr, String sPattern) {
		ArrayList<String> aList = new ArrayList<String>();
		Pattern oPattern = Pattern.compile(sPattern);
		Matcher oMatcher = oPattern.matcher(sStr);
		while (oMatcher.find()) {
			for (int i = 1, nTotal = oMatcher.groupCount(); i <= nTotal; i++) {
				aList.add(oMatcher.group(i));
			}
		}

		return aList;
	}

	/**
	 * 字符串正则表达式替换
	 * 
	 * @param sSource
	 * @param sReplace
	 * @param sPattern
	 * @return String
	 */
	public final static String getRegexReplaceResult(String sSource,
			String sReplace, String sPattern) {
		Pattern oPattern = Pattern.compile(sPattern);
		Matcher oMatcher = oPattern.matcher(sSource);

		return oMatcher.replaceAll(sReplace);
	}

	/**
	 * 正则表达式检查结果
	 * 
	 * @param sStr
	 * @param sPattern
	 * @return boolean
	 */
	public final static boolean checkMather(String sStr, String sPattern) {
		Pattern oPattern = Pattern.compile(sPattern);
		Matcher oMather = oPattern.matcher(sStr);
		return oMather.matches();
	}

	/**
	 * 如果字符串为空则用默认值
	 * 
	 * @param sStr
	 * @param sDefault
	 * @return String
	 */
	public final static String toStr(Object sStr, String sDefault) {
		return sStr == null ? sDefault : sStr.toString();
	}

	public final static String toQueryStr(Object str, String defaultValue) {
		return (str == null || str.toString().length() == 0) ? defaultValue
				: str.toString();
	}

	/**
	 * 把字符串转换成整数
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return int
	 */
	public final static int toInt(String sValue, int nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Integer.valueOf(sValue);
	}

	/**
	 * 把字符串转换成小数
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return Float
	 */
	public final static Float toFloat(String sValue, Float nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Float.valueOf(sValue);
	}

	/**
	 * 把字符串转换成浮点
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return float
	 */
	public final static float toFloat(String sValue, float nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Float.valueOf(sValue);
	}

	/**
	 * 把字符串转换成 布尔型
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return boolean
	 */
	public final static boolean toBoolean(String sValue, boolean nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Boolean.valueOf(sValue);
	}

	/**
	 * Gets the date format with "yyyy-MM-dd" form.
	 * 
	 * @param date
	 * @return
	 */
	public final static String getDateFormat(String date) {
		return date.trim().substring(0, 10);
	}

	public final static String getDateMonthFormat(String date) {
		return date.trim().substring(0, 7);
	}

	/**
	 * 获取下一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public final static String getPreDay(String date) throws ParseException {
		DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		long sourceTime = dataFormat.parse(date).getTime();
		long targetTime = sourceTime + 24 * 60 * 60 * 1000;
		Date targetDate = new Date(targetTime);
		return dataFormat.format(targetDate);
	}

	/**
	 * 获取下一月
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public final static String getPreMonth(String date) throws ParseException {
		DateFormat dataFormat = new SimpleDateFormat("yyyy-MM");
		long sourceTime = dataFormat.parse(date).getTime();
		int maxDay = getMaxDay(date.split("-")[0], date.split("-")[1]);
		long targetTime = sourceTime + Long.valueOf(maxDay) * 24 * 60 * 60
				* 1000;
		Date targetDate = new Date(targetTime);
		return dataFormat.format(targetDate);
	}

	/**
	 * Gets the date time with the format 'yyyy-MM-dd'
	 * 
	 * @param date
	 * @return
	 */
	public final static String getDate2DayFormat(Date date) {
		DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dataFormat.format(date);
	}

	/**
	 * Formats the date and gets the final hour value.
	 * 
	 * @param date
	 * @return
	 */
	public final static String getDate2HourFormat(Date date) {
		DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH24");
		// System.out.println(dataFormat.format(date));
		return dataFormat.format(date);
	}

	/**
	 * Formats the date and gets the final month value.
	 * 
	 * @param date
	 * @return
	 */
	public final static String getDate2MonthFormat(Date date) {
		DateFormat dataFormat = new SimpleDateFormat("yyyy-MM");
		// System.out.println(dataFormat.format(date));
		return dataFormat.format(date);
	}

	/**
	 * Formats the date and gets the final year value.
	 * 
	 * @param date
	 * @return
	 */
	public final static String getDate2YearFormat(Date date) {
		DateFormat dataFormat = new SimpleDateFormat("yyyy");
		// System.out.println(dataFormat.format(date));
		return dataFormat.format(date);
	}

	public final static int getMaxDay(String year, String month) {
		/* The month has 31 days */
		int y = Integer.valueOf(year);
		int[] array = { 1, 3, 5, 7, 8, 10, 12 };
		int m = Integer.valueOf(month);
		System.out.println("month:" + m);
		for (int j : array) {
			if (j == m) {
				return 31;
			}
		}
		if (m == 2 && (y % 4 == 0) && ((y % 100 != 0) || (y % 400 == 0))) {
			return 29;
		} else if (m == 2) {
			return 28;
		}
		return 30;
	}

	public final static String trim(String str) {
		if (null != str) {
			return str.trim();
		}
		return str;
	}

	public final static String[] split(String str, String regex) {
		if (null != str && !"".equals(str)) {
			return str.trim().split(regex);
		}
		return null;
	}

	public final static String getDateFormat(Date date, String formatStr) {
		String str = "";
		if (date != null) {
			SimpleDateFormat dataFormat = new SimpleDateFormat(formatStr);
			str = dataFormat.format(date);
		}
		return str;
	}

	public final static String getRandom(int paramInt) {
		StringBuffer sb = new StringBuffer();
		Random rd = new Random();
		for (int i = 0; i < paramInt; i++)
			sb.append("0123456789".charAt(rd.nextInt(10)));
		return sb.toString();
	}

	public final static Date StringToDate(String paramDate, String paramString)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(paramString);
		Date localDate = sdf.parse(paramDate);
		return localDate;
	}

	public final static String getSysdateToString(String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(Calendar.getInstance().getTime()).toString();
	}

	public final static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	public final static String transitionEncode(String str) {
		String strEncode = getEncoding(str);
		try {
			if (strEncode.equals("ISO-8859-1")) {
				str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			}
			if (strEncode.equals("GBK")) {
				str = new String(str.getBytes("GBK"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 对数字串进行特殊处理，生成sql中in（）所需要的格式
	public final static String joinToString(String str) {
		StringBuffer targetString = new StringBuffer();
		String[] strArray = str.split(",");
		for (int i = 0; i < strArray.length; i++) {
			if (i > 0) {
				targetString.append(",");
			}
			String meta = "'" + strArray[i] + "'";
			targetString.append(meta);
		}

		return targetString.toString();
	}

	// 对导出文件的日期进行处理
	public final static String preTimeStr(HashMap<String, Object> paramMap) {
		String timeStr = ""; // 定义导出文件名中的时间

		// 如果开始时间不为null且不为多选的结算账期则作为文件名中的开始时间；如为结算账期则查询账期的最大值及最小值作为导出文件名的最大时间点及最小时间点
		if ((String) paramMap.get("startTime") != null) {
			String paramStartTime = (String) paramMap.get("startTime");
			// String standTime = paramStartTime.length() > 10 ?
			// paramStartTime.substring(0, 10) : paramStartTime ;

			timeStr += paramStartTime.replace(":", "_").replace("-", ""); // 获取标准格式的日期

		}

		// 如果开始时间不为null且结束时间不为null则将结束时间拼接-作为文件名的结束时间
		if ((String) paramMap.get("endTime") != null
				&& (String) paramMap.get("startTime") != null) {
			String paramEndTime = (String) paramMap.get("endTime");
			// String standTime = paramEndTime.length() > 10 ?
			// paramEndTime.substring(0, 10) : paramEndTime ;
			timeStr += "-" + paramEndTime.replace(":", "_").replace("-", "");

		} else if ((String) paramMap.get("endTime") != null) {
			String paramEndTime = (String) paramMap.get("endTime");
			// String standTime = paramEndTime.length() > 10 ?
			// paramEndTime.substring(0, 10) : paramEndTime ;
			timeStr += paramEndTime.replace(":", "_").replace("-", "");
		}
		// 如果查询年份不为null则将查询年份作为文件名中的时间
		if ((String) paramMap.get("ymDate") != null) {
			timeStr += ((String) paramMap.get("ymDate")).replace("-", "");
		}
		// 如果年份存在，则取年份作为文件名中的时间
		if ((String) paramMap.get("year") != null) {
			timeStr += ((String) paramMap.get("year")).replace("-", "");
		}
		// 内蒙古出账收入中，查询月份为checkMonth
		if ((String) paramMap.get("checkMonth") != null) {
			timeStr += ((String) paramMap.get("checkMonth")).replace("-", "");
		}
		// 门户年度累计统计UV,查询年份checkYear
		if ((String) paramMap.get("checkYear") != null) {
			timeStr += ((String) paramMap.get("checkYear")).replace("-", "");
		}
		// 结算账期时间格式处理
		if ((String) paramMap.get("settleTime") != null) {
			// String[] timeArr =
			// ((String)paramMap.get("settleTime")).split(",");
			// int len = timeArr.length;
			// String settleTime = len > 1 ? timeArr[len-1] + "-" + timeArr[0] :
			// timeArr[0];
			timeStr = paramMap.get("settleTime").toString();
		}

		// 如果最终timeStr不为空，则加上括号，否则不加
		if (!"".equals(timeStr)) {
			timeStr = "(" + timeStr + ")";
		}

		return timeStr;
	}
}