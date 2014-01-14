package com.keicei.agent.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 * 
 * @author ZHANGYAN
 * 
 */
public class User {
	private String id, name, password, role;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 角色 1=拓展员 2=监察员 3=管理员
	 * 
	 * @return
	 */
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private static final Map<String, String> roleName = new HashMap<String, String>(
			3);
	static {
		roleName.put("3", "管理员");
		roleName.put("2", "监察员");
		roleName.put("1", "拓展员");
	}

	public String getRoleName() {
		String name = roleName.get(role);
		return name == null ? "未知角色" : name;
	}
}
