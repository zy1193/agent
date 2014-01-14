package com.keicei.agent.domain.entity;

/**
 * 商品信息
 * 
 * @author ZHANGYAN
 * 
 */
public class Product {
	/** 商品编号，名称，单价(金额单位为厘)，类型，类型描述 **/
	private String id, name,  type, typeDesc;
	private int price;

	public Product() {
		super();
	}

	public Product(String id, String name, int price, String type) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

}
