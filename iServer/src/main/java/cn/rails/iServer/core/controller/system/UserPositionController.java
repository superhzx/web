package cn.rails.iServer.core.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.Position;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.entity.UserPosition;
import cn.rails.iServer.core.service.system.PositionDepartmentService;
import cn.rails.iServer.core.service.system.PositionService;
import cn.rails.iServer.core.service.system.UserPositionService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.workflow.WorkFlowException;

import com.alibaba.fastjson.JSON;
/**
 * @author       : wangqi
 * @date         ：2017-03-24
 * @description  ：用户岗位
 */
@RequestMapping(value ="/userPosition",produces="text/html;charset=UTF-8")
@Controller
public class UserPositionController{
	
	@Autowired
	private UserPositionService userPositionService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private UserService userService;
	@Autowired
	private PositionDepartmentService positionDepartmentService;
	
	/**
	 * 用户信息保存
	 * @param users 人员信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save() {
		Map<String, Object> map = new HashMap<String, Object>();
		try { 
			String userId = request.getParameter("userId");
			String positionIds = request.getParameter("positionIds");
			String mainPosition = request.getParameter("mainPosition");
			String[] poIds = positionIds.split(",");
			userPositionService.deleteUserPositionByUserId(userId);
			if(mainPosition.length() == 32){
				UserPosition userPosition = new UserPosition();
				userPosition.setId(UUIDHexGenerator.getUUID());
				userPosition.setUserId(userId);
				userPosition.setPositionId(mainPosition);
				userPosition.setIsMain("1");
				userPositionService.save(userPosition);
				
				//同步到BPM
				User user =  userService.queryById(userPosition.getUserId());
				PositionDepartment positionDepartment = positionDepartmentService.getDepartmentIdByPosition(userPosition.getPositionId());
				String result = workFlowService.UpdateUser(user.getCode(), user.getName(), positionDepartment.getDepartments().getCode());
				if(!JSON.parseObject(result).getString("IsSuccess").equals("true")){
					//同步成功
				}
			}
			for(String positionId:poIds){
				UserPosition userPosition = new UserPosition();
				userPosition.setId(UUIDHexGenerator.getUUID());
				userPosition.setUserId(userId);
				userPosition.setPositionId(positionId);
				userPosition.setIsMain("0");
				userPositionService.save(userPosition);
			}
			map = Common.getTipMsg("save", 1);
		} catch (Exception e) {
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 用户岗位信息
	 * @param userPosition 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteby(UserPosition userPosition) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//删除人员表中相关信息
			userPositionService.delete(userPosition);
			map = Common.getTipMsg("delete", 0);
		} catch (Exception e) {
			map = Common.getTipMsg("delete", 1);
		}
		return JSON.toJSONString(map);
	}
}
