package com.AlyxGreen;

/**
 * This is just a class where I stick functions I want to have around but that
 * aren't directly related to mapping or reducing
 */
public class UnimportantFunctions {
	public static int[] randomArray(int length){
		int max = 100;
		int[] out = new int[length];
		for (int i = 0; i < length; i++){
			out[i] = Math.round((float) Math.random() * max);
		}
		return out;
	}
	public static int[] randomArray(int length, int max){
		if (max < 5){
			max = 5;
		}
		int[] out = new int[length];
		for (int i = 0; i < length; i++){
			out[i] = Math.round((float) Math.random() * max);
		}
		return out;
	}
}
