package com.yl.payinterface.front.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * xml工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月17日
 * @version V1.0.0
 */
public class XmlUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMapFromXml(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map<String, String> m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			m.put(k, v);
		}

		// �ر���
		in.close();
		return m;
	}

	@SuppressWarnings("rawtypes")
	public static SortedMap<Object, Object> getSortedMapFromXml(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		SortedMap<Object, Object> m = new TreeMap<Object, Object>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// �ر���
		in.close();

		return m;
	}

	/**
	 * ��ȡ�ӽ���xml
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	@SuppressWarnings("rawtypes")
	public static String parseXML(SortedMap parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {

				sb.append("<" + k + ">" + parameters.get(k) + "</" + k + ">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
}