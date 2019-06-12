package com.yl.client.front.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class FileUtil {

	/**
	 * 读取指定文件里的内容
	 */
	public static byte[] input(String path) throws IOException {
		File f = new File(path);
		InputStream is = new FileInputStream(f);
		byte[] b = new byte[is.available()];
		int islen = is.available();
		// 每次读取的偏移量
		int offset = 1024;
		int count = islen / offset;
		for (int i = 0; i < count; i++) {
			while ((is.read(b)) != -1) {
				is.read(b, offset * i, offset * (i + 1));
			}
		}
		is.read(b, offset * count, is.available());
		is.close();
		return b;
	}

	/**
	 * 将f1复制到path2路径,文件名为fileName
	 */
	public static void copy(File f1, String path2, String fileName) throws IOException {
		File f2 = new File(path2);
		if (!f2.exists()) {
			f2.mkdirs();
		}

		InputStream input = new FileInputStream(f1);
		OutputStream output = new FileOutputStream(path2 + "/" + fileName);
		int t = 0;
		byte[] buf = new byte[4096];
		while ((t = input.read(buf)) != (-1)) {
			output.write(buf,0,t);
		}
		input.close();
		output.close();
	}

	/**
	 * 删除指定文件
	 */
	public static void delete(String fileName) {
		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		} else {
			System.out.println("文件不存在");
		}
	}
	
}
