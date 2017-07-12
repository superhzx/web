package cn.rails.iServer.core.controller.req;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import sun.misc.BASE64Encoder;
import cn.rails.iServer.core.entity.Documents;
import cn.rails.iServer.core.service.system.DocumentsService;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author wl
 * @date 2017年5月5日
 * @description 上传文件
 */
@RequestMapping(value ="/documents",produces="text/html;charset=UTF-8")
@Controller
public class DocumentsController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private DocumentsService documentsService;
	
	/*
	 * 上传的文件或图片保存到数据
	 * */
	@RequestMapping(value="/UploadServlet",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String UploadServlet() {
		String flag = "false";
		String documentId = request.getParameter("documentId");  //文件ID
		String filename = request.getParameter("filename");  //文件名称（完整的）
		String filePath = request.getParameter("filePath");  //完整的上传路径
        String ext = request.getParameter("ext");  //扩展名
        String saveFileName = request.getParameter("saveFileName");  //文件名称（时间的）
        String size = request.getParameter("size");  //文件大小
        String userId=request.getParameter("userId"); 
        String reqId=request.getParameter("reqId");
		Documents documents=new Documents();
		if(!documentId.equals("")){
			documents=documentsService.queryById(documentId);//文档信息
		}else{
			
		}
        //documents.setContractId(documentId);//GID(发票、合同、中标书)
		documents.setCreateDate(new Date());//创建时间
		documents.setFileName(filename);//原文件名称
		documents.setFileSaveName(saveFileName);//存在的文件名称
		documents.setFilePath(filePath);//路径
		documents.setFileType(ext);//文件类型
		documents.setFileSize(size);
		documents.setReqId(reqId);
		//登陆信息
		//Users users = userService.queryById(userId);
		documents.setCreaterUserid(userId);
		if (documents.getId()==null) {
			documents.setId(UUIDHexGenerator.getUUID());
			documentsService.save(documents);
			flag="true";
		} else {
			documentsService.update(documents);
			flag="true";
		}
		return flag;
	}
	
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/download",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String download(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		Documents doc = null;
		//根据ID直接获取文档信息
		doc=documentsService.queryById(id);//文档信息
		String fileString = "";
		if(doc!=null){
			byte[] body = null;
			File file=new File(doc.getFilePath());
			if (file.exists()) {
				InputStream is = null;
				try {
					is = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					body = new byte[is.available()];
					is.read(body);
					is.close();
				fileString = new BASE64Encoder().encode(body);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		

		}
		return fileString;
	}
	
	/**
	 * 查询文件
	 */
	@RequestMapping("/checkDownLoad")
	@ResponseBody
	public String checkDownLoad() {
		String reqId=request.getParameter("reqId");//业务表ID
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Paramter param = new Paramter();
			param.addCondition("reqId", reqId);
			PageTion documents=documentsService.listByPage(1, 1000, param);
			if(documents.getList().size()>0){
				map.put("status_code", "1");
				map.put("fileNum", documents.getNum());
				map.put("files", documents.getList());
				return JSON.toJSONString(map);
			}else{
				return "{\"status_code\": \"0\",\"download\": \"\",\"filename\": \"\"}";
			}
		} catch (Exception e) {
			return "{\"status_code\": \"0\",\"download\": \"\",\"filename\": \"\"}";
		}
	}
}
