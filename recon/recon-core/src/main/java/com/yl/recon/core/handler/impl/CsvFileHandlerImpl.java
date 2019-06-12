package com.yl.recon.core.handler.impl;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.recon.core.handler.AbstractFileHandler;
import com.yl.recon.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * csv文件处理
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
public class CsvFileHandlerImpl extends AbstractFileHandler {
	private static Logger logger = LoggerFactory.getLogger(CsvFileHandlerImpl.class);


	@Override
	public List <?> fileHandle() {
		String lineTxt;
		String[] titles = null;
		String[] totalTitles = null;
		FileInputStream fileInputStream = null;
		InputStreamReader read = null;
		final String totalTitlesKey = "totalNum";
		boolean headFlag = true;
		boolean Flag = false;
		List <String> list = new ArrayList <>();
		try {
			fileInputStream = new FileInputStream(file);
			read = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if (headFlag) {
					headFlag = false;
					if (StringUtils.notBlank(this.separator)) {
						titles = lineTxt.split(this.separator);
						if (Flag) {
							list.add(FileUtil.contentConvert(totalTitles, lineTxt.split(separator)));
						}
					} else {
						this.separator = lineTxt.indexOf(" ") > 0 ? (" ") : lineTxt.indexOf(",") > 0 ? (",") :
								lineTxt.indexOf("|") > 0 ? ("|") : null;
						if (StringUtils.isBlank(this.separator)) {
							throw new RuntimeException("file text separator is error!");
						}
					}
					continue;
				} else {
					if (lineTxt.indexOf(totalTitlesKey) > -1) {
						totalTitles = lineTxt.split(this.separator);
						headFlag = true;
					}
					if (headFlag) {
						Flag = true;
						//list.add(contentConvert(totalTitles, lineTxt.split(this.separator)));
						continue;
					}
					list.add(FileUtil.contentConvert(titles, lineTxt.split(this.separator)));
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				if (null != fileInputStream) {
					fileInputStream.close();
				}
				if (null != read) {
					read.close();
				}
			} catch (IOException e) {
				logger.error("关闭流失败", e);
			}
		}
		return list;
	}

	@Override
	public List <?> fileHandle(String separator) {
		this.separator = separator;
		return fileHandle();
	}

}