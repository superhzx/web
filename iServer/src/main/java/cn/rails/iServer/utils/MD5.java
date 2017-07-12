package cn.rails.iServer.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public MD5() {
	 }
	 private final static String[] hexDigits = { 
	       "0", "1", "2", "3", "4", "5", "6", "7", 
	       "8", "9", "a", "b", "c", "d", "e", "f"}; 
	 
	 public static byte[] md5(byte[] in) {
		MessageDigest md = null;
//	  	byte[] out = new byte[16];
		byte[] out = null;
		try {
		   md = MessageDigest.getInstance("MD5");
		   md.reset();
		   md.update(in);
		   out = md.digest();
		} catch (NoSuchAlgorithmException ex) {
//	   		System.out.println(ex.toString());
		}
		return out;
	 }
	 
	 public static byte[] md5(byte[] in, int len) {
		  MessageDigest md = null;
//		  byte[] out = new byte[16];
		  byte[] out = null;
		  try {
			   md = MessageDigest.getInstance("MD5");
			   md.reset();
			   md.update(in, 0, len);
			   out = md.digest();
		  } catch (NoSuchAlgorithmException ex) {
	//	   		System.out.println(ex.toString());
		  }
		  return out;
	 }
	 
	 public static String byteArrayToHexString(byte[] b) {
	  StringBuffer resultSb = new StringBuffer();
	  for (int i = 0; i < b.length; i++) {
	   resultSb.append(byteToHexString(b[i]));
	  }
	  return resultSb.toString();
	 }
	 
	 private static String byteToHexString(int b) {
	  int n = b;
	  if (n < 0)
	   n = 256 + n;
	  int d1 = n / 16;
	  int d2 = n % 16;
	  return hexDigits[d1] + hexDigits[d2];
	 }
	 
	 public static void main(String[] args) {
	  byte[] tmp = md5("admin".getBytes());
	  System.out.println(tmp.length);
	  System.out.println(byteArrayToHexString(tmp));
	  System.out.println(MD5.byteArrayToHexString(MD5.md5("888888".getBytes())));
	 
	 }
	 
}
