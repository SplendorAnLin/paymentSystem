package com.yl.pay.pos.valuelist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.jdbc.AbstractDynaJdbcAdapter;
import net.mlw.vlh.adapter.jdbc.dynabean.fix.ResultSetDynaClass;

/**
 * ValueList
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class MapAdapter extends AbstractDynaJdbcAdapter{
	
	public List processResultSet(String name, ResultSet result,	int numberPerPage, ValueListInfo info) throws SQLException {
		List list = new ArrayList();

		ResultSetDynaClass rsdc = new ResultSetDynaClass(result,  false,	isUseName());

		int rowIndex = 0;
		for (Iterator rows = rsdc.iterator(); rows.hasNext()
				&& rowIndex < numberPerPage; rowIndex++) {

			try {

				DynaBean oldRow = (DynaBean) rows.next();
				Map newRow = new HashMap();

				DynaProperty[] properties = oldRow.getDynaClass().getDynaProperties();
				for (int i = 0, length = properties.length; i < length; i++) {

					String propertyName = properties[i].getName();
					Object value = oldRow.get(propertyName);
					newRow.put(propertyName, value);
				}
				list.add(newRow);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
