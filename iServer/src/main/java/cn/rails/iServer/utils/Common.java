/**
 * 
 */
package cn.rails.iServer.utils;

import java.util.HashMap;
import java.util.Map;

import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;

/**
 * @author renyf
 * @date 2017年3月23日
 * @description 
 */
public class Common {
	
	/**
	 * 添加、修改或删除操作后返回的提示信息
	 * @param operType
	 * @param flag,1:成功,0:失败,2:异常
	 * @return
	 */
	public static Map<String, Object> getTipMsg(String operType, int flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(flag==1){
			switch(operType)
			{
				case "add":
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
					map.put("msg", Message.ADDSUCCESS.getMsg());
				case "edit":
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
					map.put("msg", Message.MODIFYSUCCESS.getMsg());
				case "delete":
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
					map.put("msg", Message.DELETESUCCESS.getMsg());
			}
		} else if(flag==0){
			switch(operType)
			{
				case "add":
					map.put("status_code", StatusCode.ERROR.getStatusCode());
					map.put("msg", Message.ADDFAIL.getMsg());
				case "edit":
					map.put("status_code", StatusCode.ERROR.getStatusCode());
					map.put("msg", Message.MODIFYFAIL.getMsg());
				case "delete":
					map.put("status_code", StatusCode.ERROR.getStatusCode());
					map.put("msg", Message.DELETEFAIL.getMsg());
			}
		}else{
			map.put("status_code", StatusCode.ERROR.getStatusCode());
			map.put("msg", Message.FAIL.getMsg());
		}
		return map;
	}
}
