package com.keicei.agent.domain.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.keicei.agent.domain.entity.Goods;
import com.keicei.agent.domain.entity.Product;
import com.keicei.agent.domain.repository.ProductRepository;
import com.keicei.agent.persistence.mapper.GoodsMapper;

@Repository("productRepository")
public class ProductRepositoryImpl implements ProductRepository {

	private List<Product> productList, t1, t2;
	private Map<String, Product> productMap;
	private Map<String, List<Product>> productCategoryMap;

	@Resource
	private GoodsMapper goodsMapper;

	@PostConstruct
	public void postConstruct() {
		productList = new ArrayList<Product>(2);

		List<Goods> list = goodsMapper.list();
		for (Goods goods : list) {
			if ("money".equals(goods.getCategory())) {
				productList.add(new Product(String.valueOf(goods.getId()),
						goods.getName(), goods.getPrice(), "1"));
			} else if ("pkg".equals(goods.getCategory())) {
				productList.add(new Product(String.valueOf(goods.getId()),
						goods.getName(), goods.getPrice(), "2"));
			}

		}

		productMap = new HashMap<String, Product>(productList.size());
		for (Product product : productList) {
			productMap.put(product.getId(), product);
		}

		t1 = new ArrayList<Product>();
		t2 = new ArrayList<Product>();
		for (Product product : productList) {
			if (product.getType().equals("1"))
				t1.add(product);
			else if (product.getType().equals("2"))
				t2.add(product);
		}

		productCategoryMap = new HashMap<String, List<Product>>(2);
		productCategoryMap.put("1", t1);
		productCategoryMap.put("2", t2);

	}

	@Override
	public List<Product> list() {
		return productList;
	}

	@Override
	public List<Product> list(String type) {
		return productCategoryMap.get(type);
	}

	@Override
	public Product select(String id) {
		return productMap.get(id);
	}

}
