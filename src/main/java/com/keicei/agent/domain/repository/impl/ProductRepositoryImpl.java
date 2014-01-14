package com.keicei.agent.domain.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.keicei.agent.domain.entity.Product;
import com.keicei.agent.domain.repository.ProductRepository;

@Repository("productRepository")
public class ProductRepositoryImpl implements ProductRepository {

	private List<Product> productList, t1, t2, t3, t4,t24,t25,t26;
	private Map<String, Product> productMap;
	private Map<String, List<Product>> productCategoryMap;

	@PostConstruct
	public void postConstruct() {
		productList = new ArrayList<Product>(38);

		// productList.add(new Product("20005", "0.5元充值卡", 50, "1"));
		productList.add(new Product("20010", "1元充值卡", 1000, "1"));
		productList.add(new Product("20024", "2元充值卡", 2000, "1"));
		productList.add(new Product("20034", "3元充值卡", 3000, "1"));
		productList.add(new Product("20054", "5元充值卡", 5000, "1"));

		productList.add(new Product("40010", "10元充值卡", 10000, "1"));
		productList.add(new Product("40018", "18元充值卡", 18000, "1"));
		productList.add(new Product("40030", "30元充值卡", 30000, "1"));
		productList.add(new Product("40035", "35元充值卡", 35000, "1"));
		productList.add(new Product("40040", "40元充值卡", 40000, "1"));
		productList.add(new Product("40050", "50元充值卡", 50000, "1"));
		productList.add(new Product("40100", "100元充值卡", 100000, "1"));

		productList.add(new Product("40125", "125元充值卡", 125000, "2"));
		productList.add(new Product("40140", "140元充值卡", 140000, "2"));
		productList.add(new Product("40150", "150元充值卡", 150000, "2"));
		productList.add(new Product("40200", "200元充值卡", 200000, "2"));
		productList.add(new Product("40300", "300元充值卡", 300000, "2"));
		productList.add(new Product("40500", "500元充值卡", 500000, "2"));
		productList.add(new Product("40800", "800元充值卡", 800000, "2"));
		productList.add(new Product("41000", "1000元充值卡", 1000000, "2"));
		productList.add(new Product("41020", "1020元充值卡", 1020000, "2"));

		productList.add(new Product("14", "50元包月[直拨]", 50000, "3"));
		productList.add(new Product("15", "100元包月[直拨]", 100000, "3"));
		productList.add(new Product("16", "150元包月[直拨]", 150000, "3"));
		productList.add(new Product("17", "450元包月[直拨]", 450000, "3"));

		productList.add(new Product("12", "100元包月[回拨]", 100000, "4"));
		productList.add(new Product("13", "300元包月[回拨]", 300000, "4"));
		productList.add(new Product("18", "50元包月[回拨]", 50000, "4"));
		//VIP会员卡
		productList.add(new Product("30300", "1年VIP会员(面额300,售价352)", 352000, "24"));
		productList.add(new Product("30600", "2年VIP会员(面额600,售价528)", 528000, "24"));
		productList.add(new Product("31300", "3年VIP会员(面额1300,售价880)", 880000, "24"));
		//电脑包月卡：
         productList.add(new Product("51100", "1月100元包月(面额100,售价176)", 176000, "25"));
		 productList.add(new Product("52100", "2月50元包月(面额100,售价176)", 176000, "25"));
		 productList.add(new Product("53090", "3月30元包月(面额90,售价158)", 158000, "25"));
		 productList.add(new Product("56090", "6月15元包月(面额90,售价158)", 158000, "25"));
		 //手机包月卡：
		 productList.add(new Product("61100", "1月100元包月(面额100,售价176)",176000,"26"));
		 productList.add(new Product("62100", "2月50元包月(面额100,售价176)",176000,"26"));
		 productList.add(new Product("63090", "3月30元包月(面额90,售价158)",158000,"26"));
		 productList.add(new Product("66090", "6月15元包月(面额90,售价158)",158000,"26"));

		productMap = new HashMap<String, Product>(productList.size());
		for (Product product : productList) {
			productMap.put(product.getId(), product);
		}

		t1 = new ArrayList<Product>();
		t2 = new ArrayList<Product>();
		t3 = new ArrayList<Product>();
		t4 = new ArrayList<Product>();
		t24 = new ArrayList<Product>();
		t25 = new ArrayList<Product>();
		t26 = new ArrayList<Product>();
		for (Product product : productList) {
			if (product.getType().equals("1"))
				t1.add(product);
			else if (product.getType().equals("2"))
				t2.add(product);
			else if (product.getType().equals("3"))
				t3.add(product);
			else if (product.getType().equals("4"))
				t4.add(product);
			else if(product.getType().equals("24"))
				t24.add(product);
			else if(product.getType().equals("25"))
				t25.add(product);
			else if(product.getType().equals("26"))
				t26.add(product);
		}

		productCategoryMap = new HashMap<String, List<Product>>(7);
		productCategoryMap.put("1", t1);
		productCategoryMap.put("2", t2);
		productCategoryMap.put("3", t3);
		productCategoryMap.put("4", t4);
		productCategoryMap.put("24", t24);
		productCategoryMap.put("25", t25);
		productCategoryMap.put("26", t26);

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
