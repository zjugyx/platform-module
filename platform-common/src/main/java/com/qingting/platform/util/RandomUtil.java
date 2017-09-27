package com.qingting.platform.util;

import java.util.Random;

public class RandomUtil {
	/**
	 * 
	 * 方法作用：生成指定个字符随机数
	 */
	public static String getRandomCode(int length){
		//字符串转char数组
		char[] str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	
		StringBuilder sb=new StringBuilder();
		Random random=new Random();
		for(int i=0;i<length;i++){
			//随机生成0到str长度之间的数字做为下标
			int randomIndex=random.nextInt(str.length);
			//追加到sb对象
			sb.append(str[randomIndex]);
		}
		return sb.toString();
 	}
}
