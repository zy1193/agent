package com.keicei.agent.domain.entity;

/**
 * @author guoqidi
 * @date 2011-12-28 下午04:30:08
 * @version V1.1
 */
public class Notice {

	private int id;
	private String title;
	private String content;
	private String brandid;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
