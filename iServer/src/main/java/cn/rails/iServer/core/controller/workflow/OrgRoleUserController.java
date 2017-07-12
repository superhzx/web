package cn.rails.iServer.core.controller.workflow;

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

import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.UserRole;
import cn.rails.iServer.core.service.system.OrgRoleUserService;
import cn.rails.iServer.core.service.system.RoleService;
import cn.rails.iServer.core.service.system.UserRoleService;
import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @author hzx
 * @date 2017年4月11日
 * @description 组织角色用户
 */
@RequestMapping(value ="/orgRoleUser",produces="text/html;charset=UTF-8")
@Controller
public class OrgRoleUserController{
	
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	
	@Autowired 
	private OrgRoleUserService orgRoleUserService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private WorkFlowService workFlowService;
	
	//保存该人员的角色信息
	@RequestMapping(value = "saveOrgRoleUser")
	@ResponseBody
	public String saveOrgRoleUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String roleId = request.getParameter("roleId");
			String roleCode = request.getParameter("roleCode");
			String ids = request.getParameter("ids");
			String codes = request.getParameter("codes");
			String[] id = ids.split(",");
			orgRoleUserService.deleteByOrgRoleId(roleId);
			
			for(String uid:id){
				String userid = uid;
				if(id==null||"".equals(userid)){
					map.put("msg", Message.DELETESUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
				}else{
				
					BusinessOrgRoleUser boru = new BusinessOrgRoleUser();
					boru.setId(UUIDHexGenerator.getUUID());
					boru.setOrgRoleId(roleId);
					boru.setUserId(userid);
					orgRoleUserService.save(boru);
				}
				map.put("msg", Message.DELETESUCCESS.getMsg());
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
			}
			//同步到BPM
			/*workFlowService.UpdatePostUsers(roleCode, codes);*/
			
		} catch (Exception e) {
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}
}
