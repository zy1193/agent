package com.keicei.util;

import java.util.Map;

public class PaginationUtil {

	public static final int PAGE_SIZE = 15;
	private static final String OFFSET = "offset", LIMIT = "limit";

	public static void generatePageParameter(Map<String, Object> param,
			int page, int pageSize) {
		param.put(OFFSET, pageSize * (page - 1));
		param.put(LIMIT, pageSize);
	}
}
