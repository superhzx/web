package cn.rails.iServer.core.controller.req;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.service.system.CommentService;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 审核信息控制层
 */
@RequestMapping(value ="/comment",produces="text/html;charset=UTF-8")
@Controller
public class CommentController{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/list")
	@ResponseBody
	
	public String list(String conditionJson) throws UnsupportedEncodingException {
		// 获取分页及排序相关信息
		String instanceId = request.getParameter("instanceId");// 流程ID
		int page = Integer.parseInt(request.getParameter("page"));// 当前页
		int rows = Integer.parseInt(request.getParameter("rows"));// 行数
		String orderString = request.getParameter("sidx");// 排序字段
		String orderBy = request.getParameter("sord");// 行数
		String params = request.getParameter("params");
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		Paramter param = new Paramter();
		
		String likeSql = "";
		if(instanceId!=null&&!instanceId.equals("")&&instanceId!=""){
			instanceId= URLDecoder.decode(instanceId,"UTF-8");
			likeSql+=" and intanceId = '"+instanceId+"' ";
		}
		param.setAndSql(likeSql);
		
		// 设置排序
		Order order = new Order();
		order.setClumn(orderString);
		if (orderBy.equals("desc")) {
			order.setFalg(1);
		} else {
			order.setFalg(2);
		}
		param.addOrder(order);
		
		
		// 查询数据
		PageTion data = commentService.listByPage(page, rows, param);
		// 设置页面展示相关信息
		map.put("total", data.getTotal());// 总页数
		map.put("rows", data.getList());// 总行数
		map.put("page", data.getPageNo());// 第几页
		map.put("rowNum", data.getPageSize());// 第几页
		map.put("records", data.getNum());//总条数
		json = JSON.toJSONString(map);
		return json;
	}
}