package cn.rails.iServer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigUtil {
	public static final Properties properties = new Properties();
	
	static {
		InputStream is = null;
		try {
			is = ConfigUtil.class.getResourceAsStream("/config.properties");
			properties.load(is);
		} catch (Exception e) {
			System.out.println("加载资源失败");
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//获得每页显示记录条数
	public static String getPageSize(){
		return properties.getProperty("pageSize");
	}

	public static String replace(String content){
			Pattern p = Pattern.compile("\\s*|\\t|\\r|\\n");
			Matcher m = p.matcher(content);
			String after = m.replaceAll(""); 
			return after;
	}
}
