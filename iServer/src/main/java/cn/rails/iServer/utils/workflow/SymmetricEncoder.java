package cn.rails.iServer.utils.workflow;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;


	public class SymmetricEncoder {
		/**
		 * 加密
		 * @param sSrc 加密字符串
		 * @param sKey 加密秘钥		 
		 * @throws Exception 加密异常
		 */
		public static String Encrypt(String sSrc, String sKey) throws Exception {
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			} 
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
			IvParameterSpec iv = new IvParameterSpec("1234567890123456".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

			return new BASE64Encoder().encode(encrypted);
		}

		/**
		 * 解密
		 * @param sSrc 解密字符串
		 * @param sKey 解密秘钥		 
		 * @throws Exception 解密异常
		 */
		public static String Decrypt(String sSrc, String sKey) {
			try {
				// 判断Key是否正确
				if (sKey == null) {
					System.out.print("Key为空null");
					return null;
				}
				// 判断Key是否为16位
				if (sKey.length() != 16) {
					System.out.print("Key长度不是16位");
					return null;
				}
				if(sSrc==null){
					return null;
				}
				byte[] raw = sKey.getBytes("UTF-8");
				SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				IvParameterSpec iv = new IvParameterSpec("1234567890123456"
						.getBytes("UTF-8"));
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
				byte[] encrypted1 = new BASE64Decoder().decodeBuffer(new String(sSrc));// 先用base64解密
				try {
					byte[] original = cipher.doFinal(encrypted1);
					String originalString = new String(original,"UTF-8");
					return originalString;
				} catch (Exception e) {
					System.out.println(e.toString());
					return null;
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
				return null;
			}
		}


}
