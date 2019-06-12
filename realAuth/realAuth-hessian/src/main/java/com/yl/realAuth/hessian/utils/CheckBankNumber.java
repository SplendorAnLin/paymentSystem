package com.yl.realAuth.hessian.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckBankNumber {

	// Description: 银行卡号Luhm校验

	// Luhm校验规则：16位银行卡号（19位通用）:

	// 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
	// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
	// 3.将加法和加上校验位能被 10 整除。

	// 开头6位
	// private final static String strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";

	private final static String SUCCESS = "true";

	private final static String BAD_LENGTH = "银行卡号长度必须在15到19之间";

	private final static String NOT_NUMBER = "银行卡必须全部为数字";

	private final static String ILLEGAL_NUMBER = "银行卡不符合规则";

	public static String luhmCheck(String bankno) {
		if (bankno.length() < 15 || bankno.length() > 19) {
			return BAD_LENGTH;
		}

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(bankno);
		if (match.matches() == false) {
			return NOT_NUMBER;
		}

		// if (strBin.indexOf(bankno.substring(0, 2)) == -1) {
		// return "银行卡号开头6位不符合规范";
		// }

		int lastNum = Integer.parseInt(bankno.substring(bankno.length() - 1, bankno.length()));// 取出最后一位（与luhm进行比较）

		String first15Num = bankno.substring(0, bankno.length() - 1);// 前15或18位
		// System.out.println(first15Num);
		char[] newArr = new char[first15Num.length()]; // 倒叙装入newArr
		char[] tempArr = first15Num.toCharArray();
		for (int i = 0; i < tempArr.length; i++) {
			newArr[tempArr.length - 1 - i] = tempArr[i];
		}
		// System.out.println(newArr);

		int[] arrSingleNum = new int[newArr.length]; // 奇数位*2的积 <9
		int[] arrSingleNum2 = new int[newArr.length];// 奇数位*2的积 >9
		int[] arrDoubleNum = new int[newArr.length]; // 偶数位数组

		for (int j = 0; j < newArr.length; j++) {
			if ((j + 1) % 2 == 1) {// 奇数位
				if ((int) (newArr[j] - 48) * 2 < 9) arrSingleNum[j] = (int) (newArr[j] - 48) * 2;
				else arrSingleNum2[j] = (int) (newArr[j] - 48) * 2;
			} else // 偶数位
			arrDoubleNum[j] = (int) (newArr[j] - 48);
		}

		int[] arrSingleNumChild = new int[newArr.length]; // 奇数位*2 >9 的分割之后的数组个位数
		int[] arrSingleNum2Child = new int[newArr.length];// 奇数位*2 >9 的分割之后的数组十位数

		for (int h = 0; h < arrSingleNum2.length; h++) {
			arrSingleNumChild[h] = (arrSingleNum2[h]) % 10;
			arrSingleNum2Child[h] = (arrSingleNum2[h]) / 10;
		}

		int sumSingleNum = 0; // 奇数位*2 < 9 的数组之和
		int sumDoubleNum = 0; // 偶数位数组之和
		int sumSingleNumChild = 0; // 奇数位*2 >9 的分割之后的数组个位数之和
		int sumSingleNum2Child = 0; // 奇数位*2 >9 的分割之后的数组十位数之和
		int sumTotal = 0;
		for (int m = 0; m < arrSingleNum.length; m++) {
			sumSingleNum = sumSingleNum + arrSingleNum[m];
		}

		for (int n = 0; n < arrDoubleNum.length; n++) {
			sumDoubleNum = sumDoubleNum + arrDoubleNum[n];
		}

		for (int p = 0; p < arrSingleNumChild.length; p++) {
			sumSingleNumChild = sumSingleNumChild + arrSingleNumChild[p];
			sumSingleNum2Child = sumSingleNum2Child + arrSingleNum2Child[p];
		}

		sumTotal = sumSingleNum + sumDoubleNum + sumSingleNumChild + sumSingleNum2Child;

		// 计算Luhm值
		int k = sumTotal % 10 == 0 ? 10 : sumTotal % 10;
		int luhm = 10 - k;

		if (lastNum == luhm) {
			return SUCCESS;// 验证通过
		} else {
			return ILLEGAL_NUMBER;
		}
	}

	/**
	 * 与代付银行卡号校验统一
	 * @param cardNo
	 * @return
	 */
	public static boolean checkCard(String cardNo) {

		if (null == cardNo || cardNo.isEmpty()) return false;

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(cardNo);
		if (cardNo.length() < 14 || cardNo.length() > 19 || match.matches() == false) return false;

		String[] nums = cardNo.split("");
		int sum = 0;
		int index = 1;
		for (int i = 0; i < nums.length; i++) {
			if ((i + 1) % 2 == 0) {
				if ("".equals(nums[nums.length - index])) {
					continue;
				}
				int tmp = Integer.parseInt(nums[nums.length - index]) * 2;
				if (tmp >= 10) {
					String[] t = String.valueOf(tmp).split("");
					tmp = Integer.parseInt(t[1]) + Integer.parseInt(t[2]);
				}
				sum += tmp;
			} else {
				if ("".equals(nums[nums.length - index])) continue;
				sum += Integer.parseInt(nums[nums.length - index]);
			}
			index++;
		}
		if (sum % 10 != 0) {
			return false;
		}
		return true;
	}

}