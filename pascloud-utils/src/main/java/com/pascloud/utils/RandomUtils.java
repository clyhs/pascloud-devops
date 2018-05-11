package com.pascloud.utils;

import java.util.Random;

public class RandomUtils {
	// 定义所有的字符组成的串
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	// 定义所有的小写字符组成的串（不包括数字）
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	// 定义所有的数字字符组成的串
	public static final String numberChar = "0123456789";

	/**
	 * 产生长度为length的随机字符串（包括字母和数字）
	 * 
	 * @param length
	 * @return
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 产生长度为length的随机字符串（包括字母，不包括数字）
	 * 
	 * @param length
	 * @return
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 产生长度为length的随机小写字符串（包括字母，不包括数字）
	 * 
	 * @param length
	 * @return
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 产生长度为length的随机大写字符串（包括字母，不包括数字）
	 * 
	 * @param length
	 * @return
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 产生长度为length的'0'串
	 * 
	 * @param length
	 * @return
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 将输入的long整数值补全成为fixedlength长度的字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 将输入的int整数值补全成为fixedlength长度的字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
		}
		sb.append(strNum);
		return sb.toString();
	}

	public static void main(String[] args) {
		// 产生长度为15的随机字符串（包括字母和数字）
		System.out.println(generateString(15));
		// 产生长度为15的随机混合字符串（不分大小写，包括字母，不包括数字）
		System.out.println(generateMixString(15));
		// 产生长度为15的随机小写字符串（包括字母，不包括数字）
		System.out.println(generateLowerString(15));
		// 产生长度为15的随机大写字符串（包括字母，不包括数字）
		System.out.println(generateUpperString(15));
		// 产生长度为15的 0 串
		System.out.println(generateZeroString(15));
		// 将输入的int整数值补全成为fixedlength长度的字符串
		System.out.println(toFixdLengthString(12351, 15));
		// 将输入的long整数值补全成为fixedlength长度的字符串
		System.out.println(toFixdLengthString(12351555625555224L, 18));
	}

}