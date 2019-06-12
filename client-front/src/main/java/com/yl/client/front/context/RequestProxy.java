package com.yl.client.front.context;

import com.lefu.commons.utils.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 请求代理处理
 * @author congxiang.bai
 * @since 2015年6月3日
 */
public class RequestProxy extends HttpServletRequestWrapper {
	public static final String PARAM_NAME_INPUT_CHARSET = "inputCharset";
	public static final String PARAM_NAME_CHARSET = "charSet";
	private String uri_encoding = "UTF-8";

	public RequestProxy(HttpServletRequest request, String encoding) {
		super(request);
		if (!StringUtils.isNotBlank(encoding)) {
			this.uri_encoding = encoding;
		}
	}

	public RequestProxy(HttpServletRequest request) {
		super(request);
		if (StringUtils.isNotBlank(request.getParameter(PARAM_NAME_INPUT_CHARSET))) {
			this.uri_encoding = request.getParameter(PARAM_NAME_INPUT_CHARSET);
		}
		if (StringUtils.isNotBlank(request.getParameter(PARAM_NAME_CHARSET))) {
			this.uri_encoding = request.getParameter(PARAM_NAME_CHARSET);
		}
	}

	/**
	 * 重载getParameter
	 */
	public String getParameter(String paramName) {
		String value = super.getParameter(paramName);
		return _DecodeParamValue(value);
	}

	/**
	 * 重载getParameterMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String[]> getParameterMap() {
		Map params = super.getParameterMap();
		HashMap<String, String[]> new_params = new HashMap<String, String[]>();
		Iterator<String> iter = params.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object oValue = params.get(key);
			if (oValue.getClass().isArray()) {
				String[] values = (String[]) params.get(key);
				String[] new_values = new String[values.length];
				for (int i = 0; i < values.length; i++)
					new_values[i] = _DecodeParamValue(values[i]);

				new_params.put(key, new_values);
			}
		}
		return new_params;
	}

	/**
	 * 重载getParameterValues
	 */
	public String[] getParameterValues(String arg0) {
		String[] values = super.getParameterValues(arg0);
		for (int i = 0; values != null && i < values.length; i++)
			values[i] = _DecodeParamValue(values[i]);
		return values;
	}

	/**
	 * 参数转码
	 * @param value
	 * @return
	 */
	private String _DecodeParamValue(String value) {
		if (StringUtils.isBlank(value) || StringUtils.isBlank(uri_encoding) || StringUtils.isNumeric(value)) return value;
		try {
			return new String(value.getBytes("ISO-8859-1"), uri_encoding);
		} catch (Exception e) {}
		return value;
	}
}
