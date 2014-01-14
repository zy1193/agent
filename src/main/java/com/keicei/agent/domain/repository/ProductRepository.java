package com.keicei.agent.domain.repository;

import java.util.List;

import com.keicei.agent.domain.entity.Product;

public interface ProductRepository {
	/**
	 * 列出所有商品
	 * 
	 * @return
	 */
	List<Product> list();

	/**
	 * 按类别列出所有商品
	 * 
	 * @param type
	 * @return
	 */
	List<Product> list(String type);
	
	Product select(String id);
}
