package cn.rails.iServer.core.controller.req;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

/**
 * @author wl
 * @date 2017年5月5日
 * @description 上传文件
 */
@WebServlet("/UploadServlet")  
public class UploadServlet extends HttpServlet {	
	private static final long serialVersionUID = 1L;  
	public UploadServlet() {  
        super();  
    }  
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {  
            request.setCharacterEncoding("utf-8");  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace(); 
            PrintWriter pwout=response.getWriter();
            pwout.write("failed");
        }  
        response.setCharacterEncoding("utf-8");  
        response.setContentType("text/html");  
        //以下是获取前边传过来的参数
        String fileIn = request.getParameter("file");  //文件流
        String filePath = request.getParameter("filePath");//上传路径
        String saveFileName = request.getParameter("saveFileName");// 文件名称（时间的）
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        filePath+=format.format(new Date());//LINUX相对路径
        String fileName = request.getParameter("filename");  //文件名称（完整的）
        String path = request.getSession().getServletContext().getRealPath("upload");  //文件路径
        
        try {  
            // 对base64数据进行解码 生成 字节数组，不能直接用Base64.decode（）；进行解密  
            byte[] bFile = new BASE64Decoder().decodeBuffer(fileIn);  
            for (int i = 0; i < bFile.length; ++i) {  
                if (bFile[i] < 0) {  
                	bFile[i] += 256;  
                }  
            }  
            File file = new File(filePath, saveFileName); 
            //创建存储文件夹
            
            File targetFile = new File(filePath, ""); 
            if(!targetFile.exists()){
				targetFile.mkdirs();
			}
            if (!file.exists()) {  
                file.createNewFile();  
            }  
            FileOutputStream out = new FileOutputStream(file);  
            out.write(bFile);  
            out.flush();  
            out.close();  
            PrintWriter pwout=response.getWriter();
            pwout.write("success");
        } catch (Exception e) {  
            e.printStackTrace();  
            PrintWriter pwout=response.getWriter();
            pwout.write("failed");
        }  
	}
}
