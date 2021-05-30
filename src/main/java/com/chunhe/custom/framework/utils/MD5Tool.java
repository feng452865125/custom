package com.chunhe.custom.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zero ice 2014-6-18 下午6:33:29
 * 
 */
public class MD5Tool {
	/**
	 * 生成MD5密文
	 * 注意md5字符集编码不用可能会导致最终md5结果不同
	 * 注意utf-8编码
	 * 
	 * @param str
	 *            生进行摘要的内容
	 * @return 返回摘要16进制串
	 */
	public static String ToMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] buf = messageDigest.digest(str.getBytes("utf-8"));
			String tt = byte2HexString(buf);
//			String md5Str32 = tt;
//			String md5Str16 = tt.substring(8, 24);// buf.toString().md5Strstring(8,
//													// 24);
			return tt;

		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new SecurityException(e.getMessage());
		}
	}

	/**
	 * 将字节数组转换为16进制字符串
	 * 
	 * @param data
	 *            进行转换的字节数组
	 * @return 16进制的字符串
	 */
	public static String byte2HexString(byte[] data) {
		StringBuffer checksumSb = new StringBuffer(1024);
		for (int i = 0; i < data.length; i++) {
			String hexStr = Integer.toHexString(0x00ff & data[i]);
			if (hexStr.length() < 2) {
				checksumSb.append("0");
			}
			checksumSb.append(hexStr);
		}
		return checksumSb.toString();
	}

	public static void main(String[] args) throws Exception {
		String aa = "appid=wx053be7b51c59b5a7&body=测试测试产品&mch_id=1404784002&nonce_str=ilu9xvyjw4sjov6p3dz7baky43our3a0&notify_url=http://122.235.165.133:8180/K12Service/service/order/wechat/callback&out_trade_no=1611051704152180002&spbill_create_ip=122.235.165.133&time_expire=20161105171915&time_start=20161105170415&total_fee=1&trade_type=APP&key=E2bKEbDGj7rMtX5Hdqzhd5U83AizX2Cw";
		System.out.println(MD5Tool.ToMD5(aa));
	}
}
