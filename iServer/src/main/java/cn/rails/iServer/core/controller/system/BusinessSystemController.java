/**
 * 
 */
package cn.rails.iServer.core.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.helpers.DateTimeDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.BusinessSystem;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.service.system.BusinessRoleService;
import cn.rails.iServer.core.service.system.BusinessSystemService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

import com.alibaba.fastjson.JSON;

/**
 * @author dengjm
 * @date 2017年4月10日
 * @description 
 */
@RequestMapping(value="businesssystem" ,produces="text/html;charset=UTF-8")
@Controller
public class BusinessSystemController {
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	
	@Autowired
	private BusinessSystemService service;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private BusinessRoleService businessRoleService;
	
	@RequestMapping(value="/listByPage")
	@ResponseBody
	public String listByPage(String conditionJson) throws UnsupportedEncodingException{
		// 获取分页及排序相关信息
		int page = Integer.parseInt(request.getParameter("page"));// 当前页
		int rows = Integer.parseInt(request.getParameter("rows"));// 行数
		String orderString = request.getParameter("sidx");// 排序字段
		String orderBy = request.getParameter("sord");// 行数
		String params = request.getParameter("params");
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		Paramter param = new Paramter();
		// 设置排序
		Order order = new Order();
		order.setClumn(orderString);
		if (orderBy.equals("desc")) {
			order.setFalg(1);
		} else {
			order.setFalg(2);
		}
		param.addOrder(order);
		
		//查询条件
		String likeSql=" ";
		String name= request.getParameter("name");
		if(name!=null){
			name= URLDecoder.decode(name,"UTF-8");
			likeSql+=" and business_system_name like '%"+name+"%' ";
		}
		if(params!=null && !params.equals("()") && !params.equals("null") &&  !params.equals("")){
			likeSql+=" and id  not in  "+params;
		}
		param.setAndSql(likeSql);
		
		// 查询数据
		PageTion data = service.listByPage(page, rows, param);
		// 设置页面展示相关信息
		map.put("total", data.getTotal());// 总页数
		map.put("rows", data.getList());// 总行数
		map.put("page", data.getPageNo());// 第几页
		map.put("rowNum", data.getPageSize());// 第几页
		map.put("records", data.getNum());//总条数
		json = JSON.toJSONString(map);
		return json;
	}
	
	@RequestMapping("/queryBusinessSystemById")
	@ResponseBody
	public String queryBusinessSystemById(String id){
		System.out.println("---------------------------");
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			BusinessSystem bus=service.queryById(id);
			if (bus != null) {
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("businesssystem", bus);
				json = JSON.toJSONString(map);
			} else {
				map.put("status_code", StatusCode.ERROR.getStatusCode());
				map.put("msg", Message.FAIL.getMsg());
				json = JSON.toJSONString(map);
			}
			return json;
		}
		catch(Exception e){
			return json;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(BusinessSystem bus){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(bus.getId().isEmpty()){
				bus.setId(UUIDHexGenerator.getUUID());
				bus.setCreateDate(new Date());
				service.save(bus);
				map=Common.getTipMsg("add", 1);
			}else{
				System.out.println(bus.getBusinessSystemName());
				service.update(bus);
				map=Common.getTipMsg("edit",1);
			}
		}catch (Exception e){
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteby(BusinessSystem bus){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			//删除系统
			service.delete(bus);
			//删除业务角色
			businessRoleService.deleteBySystemId(bus.getId());
			//删除组织角色
			
			map=Common.getTipMsg("delete", 0);

		}
		catch(Exception e){
			map=Common.getTipMsg("delete", 1);
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取下拉框数据
	 * @param users 人员信息
	 */
	@RequestMapping("/getSelectData")
	@ResponseBody
	public String getSelectData() {
		try {
			List<BusinessSystem> pList=null;
			String returnValue="<option value=''>&nbsp;</option>";
			pList = service.list();
			for(int i=0;i<pList.size();i++){
				returnValue+="<option value='"+pList.get(i).getId()+"'>"+pList.get(i).getBusinessSystemName()+"</option> ";
			}
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return "<option value=''>&nbsp;</option>";
		}
	}
	
	
	/**
	 * 获取下拉框数据
	 * 对接VUE
	 */
	@RequestMapping(value="/getSelectInfo")
	@ResponseBody
	public String getSelectInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<BusinessSystem> pList=null;
			pList = service.list();
			map.put("plist", pList);
			map.put("status", "1");
			map.put("msg","操作成功");
		} catch (Exception e) {
			map.put("status", "0");
			map.put("msg","操作失败");
		}
		return JSON.toJSONString(map);
	}
}
