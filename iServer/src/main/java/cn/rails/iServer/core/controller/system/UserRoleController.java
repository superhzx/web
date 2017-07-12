package cn.rails.iServer.core.controller.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.UserRole;
import cn.rails.iServer.core.service.system.RoleService;
import cn.rails.iServer.core.service.system.UserRoleService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.UUIDHexGenerator;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @author wangqi
 * @date 2017年3月27日
 * @description 用户角色
 */
@RequestMapping(value ="/userrole",produces="text/html;charset=UTF-8")
@Controller
public class UserRoleController{
	
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired 
	private UserRoleService userRoleService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	
	//保存该人员的角色信息
	@RequestMapping(value = "saveUserRole")
	@ResponseBody
	public String saveUserRole() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String roleIds = request.getParameter("roleIds");
			String[] roIds = roleIds.split(",");
			userRoleService.deleteByUserId(userId);
			for(String rId:roIds){
				UserRole uRole = new UserRole();
				uRole.setId(UUIDHexGenerator.getUUID());
				uRole.setUserId(userId);
				uRole.setRoleId(rId);
				userRoleService.save(uRole);
			}
			map = Common.getTipMsg("save", 1);
		} catch (Exception e) {
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}
}
