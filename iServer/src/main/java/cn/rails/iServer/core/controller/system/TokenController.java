package cn.rails.iServer.core.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.utils.Constant;


/**
 * @author wq
 * @date 2017年3月17日
 * @description
 */
@RequestMapping(value="/token", produces = "text/html;charset=UTF-8")
@Controller
public class TokenController {
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping("/getToken")
	@ResponseBody
	public String getToken(){
		String loginname=request.getParameter("loginname");
		String token = Constant.saveRedis(loginname);
		return token;
	}
}
