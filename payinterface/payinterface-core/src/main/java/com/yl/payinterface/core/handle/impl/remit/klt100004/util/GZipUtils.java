package com.yl.payinterface.core.handle.impl.remit.klt100004.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP Utils
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public abstract class GZipUtils {

	// 缓冲区大小
	public static final int BUFFER = 1024;
	// 压缩后文件后缀
	public static final String EXT = ".gz";

	/**
	 * 压缩数据
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		compress(bais, baos);

		byte[] output = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return output;
	}

	/**
	 * 压缩文件<br>
	 * 删除原始文件
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void compress(File file) throws Exception {
		compress(file, true);
	}

	/**
	 * 压缩文件<br>
	 * 删除原始文件
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public static void compress(String filePath) throws Exception {
		compress(filePath, true);
	}

	/**
	 * 压缩文件
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

		compress(fis, fos);

		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 压缩数据
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void compress(InputStream is, OutputStream os)
			throws Exception {

		GZIPOutputStream gos = new GZIPOutputStream(os);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}

		gos.finish();

		gos.flush();
		gos.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @param filePath
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(String filePath, boolean delete)
			throws Exception {
		File file = new File(filePath);
		compress(file, delete);
	}

	/**
	 * 解压数据
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		decompress(bais, baos);

		data = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return data;
	}

	/**
	 * 解压文件<br>
	 * 删除原始文件
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void decompress(File file) throws Exception {
		decompress(file, true);
	}

	/**
	 * 解压文件<br>
	 * 删除原始文件
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public static void decompress(String filePath) throws Exception {
		decompress(filePath, true);
	}

	/**
	 * 解压文件
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT,
				""));
		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 解压数据
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os)
			throws Exception {

		GZIPInputStream gis = new GZIPInputStream(is);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}

		gis.close();
	}

	/**
	 * 解压文件
	 * 
	 * @param filePath
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(String filePath, boolean delete)
			throws Exception {
		File file = new File(filePath);
		decompress(file, delete);
	}

}
