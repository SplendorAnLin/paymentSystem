package com.yl.client.front.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

/**
 * 图片工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
@SuppressWarnings("restriction")
public class ImgUtil {
	/**
	 * 获取图片格式
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "resource" })
	public static String getImgType(File file) throws Exception {
		String type = "";
		if (file != null) {
			FileInputStream fis = new FileInputStream(file);
			int leng = fis.available();
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] mapObj = new byte[leng];
			buff.read(mapObj, 0, leng);
			ByteArrayInputStream bais = null;
			MemoryCacheImageInputStream mcis = null;
			try {
				bais = new ByteArrayInputStream(mapObj);
				mcis = new MemoryCacheImageInputStream(bais);
				Iterator itr = ImageIO.getImageReaders(mcis);
				while (itr.hasNext()) {
					ImageReader reader = (ImageReader) itr.next();
					if (reader instanceof GIFImageReader) {
						type = "gif";
					} else if (reader instanceof JPEGImageReader) {
						type = "jpeg";
					} else if (reader instanceof PNGImageReader) {
						type = "png";
					} else if (reader instanceof BMPImageReader) {
						type = "bmp";
					}
				}
			} finally {
				if (bais != null) {
					try {
						bais.close();
					} catch (IOException ioe) {
					}
				}
				if (mcis != null) {
					try {
						mcis.close();
					} catch (IOException ioe) {
					}
				}
			}
		}
		return type;
	}

	// 获取图片字节数组
	@SuppressWarnings("resource")
	public static byte[] getBytes(File file) {
		if (file == null) {
			return null;
		} else {
			long length = file.length();
			byte[] bytes = new byte[(int) length];
			try {
				InputStream is = new FileInputStream(file);
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
					offset += numRead;
				}
				if (offset < bytes.length) {
					throw new IOException("文件读取未完成");
				}
				is.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return bytes;
		}
	}

	// 获取路径图片字节数组
	@SuppressWarnings({ "resource", "unused" })
	public static byte[] getPathBytes(String imgUrl) {
		try {
			File file = new File("/home"+imgUrl);
			if (file == null) {
				return null;
			}else {
				long length = file.length();
				byte[] bytes = new byte[(int) length];
				try {
					InputStream is = new FileInputStream(file);
					int offset = 0;
					int numRead = 0;
					while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
						offset += numRead;
					}
					if (offset < bytes.length) {
						throw new IOException("文件读取未完成");
					}
					is.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return bytes;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取图片的高和宽
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static void getImgSize(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		BufferedImage buff = ImageIO.read(fis);
		int h = buff.getHeight();
		int w = buff.getWidth();
		System.out.println(h + "-----------------" + w);
	}

	public static int getImgHeight(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		BufferedImage buff = ImageIO.read(fis);
		int h = buff.getHeight();
		if (h >= 560) {
			h = 560;
		}
		return h;
	}

	public static int getImgWidth(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		BufferedImage buff = ImageIO.read(fis);
		int w = buff.getWidth();
		if (w >= 800) {
			w = 800;
		}
		return w;
	}

	/**
	 * 修改图片的高和宽 高800 宽560
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static File changeImg(File file) throws Exception {
		int h = getImgHeight(file);
		int w = getImgWidth(file);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage buff = ImageIO.read(fis);

		Image image = buff.getScaledInstance(w, h, Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		ImageIO.write(tag, "JPEG", file);// 输出到文件流
		return file;
	}

	/**
	 * 获取图片流
	 * 
	 * @param imgUrl
	 * @return
	 * @author qiujian 2016年10月25日
	 */
	public static InputStream findDocumentImgByName(String imgUrl) {
		try {
			File file2 = new File(imgUrl);
			BufferedImage output = ImageIO.read(file2);
			output.flush();
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut;
			imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(output, "jpg", imOut);
			return new ByteArrayInputStream(bs.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String checkImg(File f) {
		String str = "";
		if (f == null) {
		} else {
			try {
				String type = ImgUtil.getImgType(f);
				if (!"jpeg".equalsIgnoreCase(type) && !"jpg".equalsIgnoreCase(type) && !"png".equalsIgnoreCase(type)) {
					str += "上传图片必须为jpg，jpeg格式";
				}
			} catch (Exception e) {
				str += "上传图片异常";
			}
			if (f.length() > 5242880) {
				str += "单张图片大小不能超过5MB";
			}
		}
		return str;
	}

}
