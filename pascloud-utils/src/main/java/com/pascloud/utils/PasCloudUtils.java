package com.pascloud.utils;

public class PasCloudUtils {
	
	public static String getRealPath(){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	} 

	public static void main(String[] args){
	    System.out.println(PasCloudUtils.getRealPath());	
	}
}
