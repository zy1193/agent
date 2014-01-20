package com.keicei.agent.domain.entity;

/**
 * 商品
 * 
 * @author jarry
 * 
 */
public class Goods {

	int id, price, money, calltime, year, month, day;
	String brandid, name, des, category, totalFlag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCalltime() {
		return calltime;
	}

	public void setCalltime(int calltime) {
		this.calltime = calltime;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTotalFlag() {
		return totalFlag;
	}

	public void setTotalFlag(String totalFlag) {
		this.totalFlag = totalFlag;
	}

}
