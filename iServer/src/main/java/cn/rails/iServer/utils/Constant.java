package cn.rails.iServer.utils;
import java.util.Properties;
import java.util.Random;
/**
 * 
 * @author wangqi
 * @date 2017年3月24日
 * @description 工具类
 */
public class Constant {
	public static Properties properties = ConfigUtil.properties;
	//根部门ID
	public static final String rootDepart=properties.getProperty("rootdepart");
	
	public static final String IPREDIS=properties.getProperty("IP_REDIS");
	
	public static final int PORTREDIS=Integer.parseInt(properties.getProperty("PORT_REDIS"));
	
	public static final int EXPIRES_IN = Integer.parseInt(properties.getProperty("EXPIRES_IN"));
	
	public static final String OA_WORKFLOWCODE= properties.getProperty("OA_WORKFLOWCODE");
	//院邮箱申请
	public static final String EM_WORKFLOWCODE= properties.getProperty("EM_WORKFLOWCODE");
	//互联网申请
	public static final String INT_WORKFLOWCODE= properties.getProperty("INT_WORKFLOWCODE");
	//142申请
	public static final String PC_WORKFLOWCODE= properties.getProperty("PC_WORKFLOWCODE");
	//CA申请
	public static final String CA_WORKFLOWCODE= properties.getProperty("CA_WORKFLOWCODE");
	//ERP账户信息新增
	public static final String ERPACCOUNT_WORKFLOWCODE= properties.getProperty("ERPACCOUNT_WORKFLOWCODE");
	//VPS
	public static final String VPS_WORKFLOWCODE= properties.getProperty("VPS_WORKFLOWCODE");
	
	/**
	 * 是否删除
	 * @author 
	 */
	public enum IsDelete{
		// 未删除 0
		YES(0),
		// 已删除 1
		NO(1);
		private int state;
		private IsDelete(int state){
			this.state = state;
		}
		public int getState() {
			return state;
		}
	}
	
	/**
	 * 返回状态
	 * @author sunyh
	 */
	public enum StatusCode{
		//不正常
		ERROR(0),
		//正常
		CORRECT(1);
		private int statusCode;
		private StatusCode(int statusCode){
			this.statusCode = statusCode;
		}
		public int getStatusCode() {
			return statusCode;
		}
	}
	
	/**
	 * 返回信息
	 * @author sunyh
	 */
	public enum Message{
		//新增失败
		ADDFAIL("新增失败！"),
		//新增成功
		ADDSUCCESS("新增成功"),
		//修改失败
		MODIFYFAIL("修改失败"),
		//修改成功
		MODIFYSUCCESS("修改成功"),
		//删除失败
		DELETEFAIL("删除失败"),
		//删除成功
		DELETESUCCESS("删除成功"),
		//操作失败
		FAIL("操作失败"),
		//操作成功
		SUCCESS("操作成功");
		private String msg;
		private Message(String msg){
			this.msg = msg;
		}
		public String getMsg() {
			return msg;
		}
	}
	
	public static String saveRedis(String loginName){
		//登录成功后生成token
		Random rnd = new Random();
		int num = 100 + rnd.nextInt(900);
		String tempStr = loginName+System.currentTimeMillis()+num;
		String token = (MD5.byteArrayToHexString(MD5.md5(tempStr.getBytes())));
		RedisUtil redis = new RedisUtil();
		int seconds  = EXPIRES_IN*60;
		redis.setex(token, loginName,seconds);
		return token;
	}
	
	public static String getTokenValue(String tokenKey){
		RedisUtil redis = new RedisUtil();
		return redis.get(tokenKey);
	}
}

