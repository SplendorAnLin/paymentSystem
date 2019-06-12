package com.yl.pay.pos.api.interfaces;

import java.util.Map;

public interface VlhQuery {
	Map<String, Object> query(String queryId, Map<String, Object> params);
}
