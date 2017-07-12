package cn.rails.iServer.listen;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.rails.iServer.utils.Constant;

public class HandlerInterceptors implements HandlerInterceptor {

	private String returnValue = "";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		if(url.indexOf("login/getLogin")<0){
			if(url.indexOf("token/getToken")<0){
				String token = request.getHeader("token");
				System.out.println(token);
				if(!token.equals("") && token != null){
					String tokenInfo = Constant.getTokenValue(token);
					if(tokenInfo != null){
						return true;
					}else{
						returnValue = "{'status_code':1,'msg':'token验证失败'}";
						PrintWriter out = response.getWriter();  
		                out.print(returnValue);
						return false;
					}
				}else{
					returnValue = "false";
					PrintWriter out = response.getWriter();  
	                out.print(returnValue);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		modelAndView = new ModelAndView(returnValue);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
