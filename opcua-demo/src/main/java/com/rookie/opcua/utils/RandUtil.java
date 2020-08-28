package com.rookie.opcua.utils;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


public class RandUtil {

	public static Integer getRandomNum(int n){
		if(n<=0){
			n = 10;
		}
		int l = (int)Math.pow(10,n);
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sr.nextInt(l*9)+l;
	}
	
	/**将二进制转换成16进制 
	* @param buf 
	* @return  String
	*/  
	public  String parseByte2HexStr(byte buf[]) {  
	       StringBuffer sb = new StringBuffer();  
	       for (int i = 0; i < buf.length; i++) {  
	               String hex = Integer.toHexString(buf[i] & 0xFF);  
	               if (hex.length() == 1) {  
	                       hex = '0' + hex;  
	               }  
	               sb.append(hex.toUpperCase());  
	       }  
	       return sb.toString();  
	}
	/**将16进制转换为二进制 
	* @param hexStr 
	* @return  byte[]
	*/  
	public  byte[] parseHexStr2Byte(String hexStr) {  
	       if (hexStr.length() < 1)  
	               return null;  
	       byte[] result = new byte[hexStr.length()/2];  
	       for (int i = 0;i< hexStr.length()/2; i++) {  
	               int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	               int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	               result[i] = (byte) (high * 16 + low);  
	       }  
	       return result;  
	}  
	
	public static void main(String[] args) throws Exception {

		for(int i =0; i<7; i++){
			System.out.println(new Random().nextInt());
		}
	}

}
