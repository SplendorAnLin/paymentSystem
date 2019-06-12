package com.yl.boss.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class DownloadUtil extends HttpServlet{
	private static final long serialVersionUID = -4601544731755783741L;
	/**
	 * 
	 * @param request
	 * @param response
	 * @param params	包含文件名，文件路径
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response,Map<String, Object> params)
            throws ServletException, IOException {
        String fileName =params.get("fileName").toString(); //文件名
        String fileRootPath = params.get("fileUrl").toString();//目录
        File file = new File(fileRootPath + "/" + fileName);
        if (!file.exists()) {//判断文件
        	//提示
        	response.setHeader("Content-type", "textml;charset=UTF-8");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write("<script>alert('查找不到该文件！');history.go(-1);</script>");
            return;
        }
        // 处理文件名
        String realname = fileName.substring(fileName.indexOf("_") + 1);
        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        // 读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(fileRootPath + "/" + fileName);
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            // 输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        out.flush();
        // 关闭文件输入流
        in.close();
        // 关闭输出流
        out.close();
    }
}
