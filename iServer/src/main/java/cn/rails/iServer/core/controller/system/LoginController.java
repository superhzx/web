package cn.rails.iServer.core.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.core.entity.Role;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.entity.UserPosition;
import cn.rails.iServer.core.entity.UserRole;
import cn.rails.iServer.core.service.system.DepartmentService;
import cn.rails.iServer.core.service.system.PositionDepartmentService;
import cn.rails.iServer.core.service.system.RoleService;
import cn.rails.iServer.core.service.system.UserPositionService;
import cn.rails.iServer.core.service.system.UserRoleService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.utils.Constant;
import cn.rails.iServer.utils.MD5;
import cn.rails.iServer.utils.Constant.StatusCode;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author wangqi
 * @date 2017年3月24日
 * @description 登录
 */

@Controller
@RequestMapping(value="/login", produces = "text/html;charset=UTF-8")
public class LoginController {
	@Autowired
	private RoleService roleservice;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService ;
	@Autowired
	private UserPositionService userPositionService;
	@Autowired
	private PositionDepartmentService positionDepartmentService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	public static final String USERINFO = "USERINFO";
	public static final String USERNAME = "USERNAME";
	public static final String USERID = "USERID";
	public static final String LOGINNAME = "LOGINNAME";
	public static final String DEPTINFO = "DEPTINFO";
	public static final String DEPTID = "DEPTID";
	public static final String DEPTNAME = "DEPTNAME";
	public static final String ROLEINFO = "ROLEINFO";
	public static final String ROLESTRING = "ROLESTRING";
	
	/**
	 * 
	 * @param loginname
	 * @param pwd
	 * @return 用户登录
	 */
	@RequestMapping("/getLogin")
	@ResponseBody
	public String login() {
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String loginname = request.getParameter("loginname");
			String pwd = request.getParameter("pwd");
			User user= userService.queryUser(loginname, pwd);
			if(user!= null){
				String userId=user.getId();
				//人员岗位信息
				UserPosition up = userPositionService.getMainPositon(userId);
				//岗位部门信息
				PositionDepartment pd =  positionDepartmentService.getDepartmentIdByPosition(up.getPositionId());
				//部门信息
				Department d = departmentService.queryById(pd.getDepartmentId());
				//人员角色信息(多个角色)
				List<UserRole> ur= userRoleService.getUserRoleByUserId(userId);
				if(ur.size()>0){
					//角色信息
					List<Role> roleList=new ArrayList<Role>();
					//角色字符串
					String roleString="";
					for(int i=0;i<ur.size();i++){
						Role role = roleservice.queryById(ur.get(i).getRoleId());
						roleList.add(role);
						roleString+="'"+ur.get(i).getRoleId()+"',";
					}
					roleString=roleString.substring(0,roleString.length()-1);
					//设置登陆人员信息
					httpSession.setAttribute(USERINFO, user);//人员信息
					httpSession.setAttribute(USERNAME, user.getName());//人员姓名
					httpSession.setAttribute(USERID, user.getId());//人员ID
					httpSession.setAttribute(LOGINNAME, user.getCode());//登录名称
					httpSession.setAttribute(DEPTINFO, d);//部门信息
					httpSession.setAttribute(DEPTID, d.getId());//当前部门ID
					httpSession.setAttribute(DEPTNAME, d.getName());//当前部门名称
					httpSession.setAttribute(ROLEINFO, roleList);//当前角色List
					httpSession.setAttribute(ROLESTRING, roleString);//当前角色String
					//设置返回前端的人员相关信息
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
					map.put("msg", "登录成功！");
					map.put("USERINFO", user);//人员信息
					map.put("USERNAME", user.getName());//人员姓名
					map.put("USERID", user.getId());//人员ID
					map.put("LOGINNAME", user.getCode());//登录名称
					map.put("DEPTID", d.getId());//当前部门ID
					map.put("DEPTNAME", d.getName());//当前部门名称
					map.put("ROLEINFO", roleList);//当前角色List
					map.put("ROLESTRING", roleString);//当前角色String
					//保存token到redis并传到app端
					String token = Constant.saveRedis(user.getCode());
					map.put("TOKEN", token);
					json = JSON.toJSONString(map);
					return json;
				}else{
					httpSession.invalidate();
					return "{\"status_code\": \"0\",\"msg\": \"该用户无权限，请联系管理员！\"}";
				}
			}else{
				httpSession.invalidate();
				return "{\"status_code\": \"0\",\"msg\": \"用户名或密码错误，登录失败！\"}";
			}
		} catch (Exception e) {
			return "{\"status_code\": \"0\",\"msg\": \"用户名或密码错误，登录失败！\"}";
		}
	}
}
