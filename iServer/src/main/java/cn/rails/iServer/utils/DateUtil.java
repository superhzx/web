package cn.rails.iServer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat ymdhms = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	public static Date toDate(String str) {
		try {
			return ymd.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toChar(Date date) {
		return ymd.format(date);
	}
	
	
	public static String sysDate(){
		return ymd.format(new Date());
	}
	
	public static String sysDate(SimpleDateFormat sdf){
		return sdf.format(new Date());
	}
	 
	public static Date dayDate(){
		try {
			return pdayDate(ymd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static Date pdayDate(SimpleDateFormat sdf) throws ParseException{
		Date date = new Date();
		String a = sdf.format(date);
		return sdf.parse(a);
	}
	
	/**
	 * rang-picker 分割时间字符串 返回时间数组 str 分割的字符串,  reg 分隔符如: '/','-'
	 * @param str
	 * @param reg 
	 * @return
	 * @throws ParseException
	 */
	public static Date[] convertByReg(String str,String reg) throws ParseException{
		Date start = null;
		Date end = null;
		String []dates = str.split(reg);
		start =  ymd.parse(dates[0].trim().replace("/", "-"));
		end =  ymd.parse(dates[1].trim().replace("/", "-"));
		Date []devDate ={start,end}; 
		return devDate;
	}
}
