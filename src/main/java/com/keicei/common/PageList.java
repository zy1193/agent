package com.keicei.common;

import java.util.List;

/**
 * 要分页的manager实现这个接口
 * 
 * @param E
 *            实体
 * @author ZHANGYAN
 * 
 */
public interface PageList<E> {
	/** 每页行数 **/
	public static final int PAGE_SIZE = 15;

	void setPage(int page);

	int getPage();

	void setEntities(E entities);

	List<E> getEntities();
}
