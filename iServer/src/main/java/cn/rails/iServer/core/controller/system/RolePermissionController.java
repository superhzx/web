package cn.rails.iServer.core.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.RolePermission;
import cn.rails.iServer.core.service.system.RolePermissionService;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;

import com.alibaba.fastjson.JSON;

/**
 * @author wl
 * @date 2017年3月23日
 * @description 角色权限
 */
@Controller
@RequestMapping(value = "/rolePermission", produces = "text/html;charset=UTF-8")
public class RolePermissionController {
	@Autowired
	private RolePermissionService service;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 保存角色菜单
	 * @param role 角色信息
	 */
	@RequestMapping("/saveRolePermission")
	@ResponseBody
	public String saveRolePermission() {
		String id = request.getParameter("id");
		String permissionId = request.getParameter("permissionId");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			service.deleteByCondition("role_id", id);
			RolePermission rolePermission = null;
			for(int i=0;i<permissionId.split("@").length;i++){
				rolePermission = new RolePermission();
				String privilegeId=permissionId.split("@")[i];
				rolePermission.setRoleId(id);
				rolePermission.setPermissionId(privilegeId);
				rolePermission.setId(UUIDHexGenerator.getUUID());
				service.save(rolePermission);
			}
			map.put("msg", Message.SUCCESS.getMsg());
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
		} catch (Exception e) {
			map.put("msg", Message.FAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
			return JSON.toJSONString(map);
		}
		return JSON.toJSONString(map);
	}
	/**
	 * 获取树节点信息
	 */
	@RequestMapping("/getSelectedStatus")
	@ResponseBody
	public String getSelectedStatus() {
		List<RolePermission> pList = null;
		String id = request.getParameter("id");
		String returnValue = "";
		pList = service.queryByCondition("roleId",id);
		for (int i = 0; i < pList.size(); i++) {
			returnValue += pList.get(i).getPermissionId() + "@";
		}
		return returnValue;
	}
}
