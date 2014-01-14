package com.keicei.agent.domain.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * 
 * @author ZHANGYAN
 * 
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = -5761561462985551871L;
	private int id, superId;
	private String name, url, category, rank, visible;
	/** 下级菜单 **/
	private List<Menu> subMenus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getSuperId() {
		return superId;
	}

	public void setSuperId(int superId) {
		this.superId = superId;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

}
