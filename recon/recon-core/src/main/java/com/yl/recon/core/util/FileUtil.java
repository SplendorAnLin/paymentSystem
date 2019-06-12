package com.yl.recon.core.util;

import com.yl.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月17
 * @desc 文件工具
 **/
public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 对账：文件标题和数据对应关系
	 *
	 * @param title
	 * @param content
	 * @return
	 */
	public static String contentConvert(String[] title, String[] content) {
		boolean lastIsNull = false;
		Map <String, String> params = new HashMap <>();
		if (title.length == content.length + 1) {
			//切割后最后一列为空的情况
			lastIsNull = true;
		}
		for (int i = 0; i < title.length; i++) {
			if (lastIsNull) {
				if (i == title.length - 1) {
					params.put(title[i], "");
					continue;
				}
			}
			try {
				params.put(title[i], content[i]);
			} catch (Exception e) {
				logger.error(content[i]);
			}
		}
		return JsonUtils.toJSONString(params);
	}
}
