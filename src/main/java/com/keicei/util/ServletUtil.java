package com.keicei.util;

import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

	public static final String href(HttpServletRequest req) {
		String ip = req.getRemoteAddr();
		String method = req.getMethod();
		String uri = req.getRequestURI();
		String queryString = req.getQueryString();
		String parameters = null;
		if (queryString == null) {
			@SuppressWarnings("rawtypes")
			Enumeration enu = req.getParameterNames();
			StringBuilder buff = new StringBuilder();
			String name, value;
			for (;;) {
				if (enu.hasMoreElements()) {
					name = (String) enu.nextElement();
					value = req.getParameter(name);
					buff.append('&').append(name).append('=').append(value);
				} else {
					break;
				}
			}
			parameters = buff.toString();
		}

		StringBuilder href = new StringBuilder();
		href.append('[').append(ip).append(']');
		href.append('[').append(method).append(']');
		href.append('[').append(uri).append(']');
		if (queryString != null)
			href.append('[').append(queryString).append(']');
		if (parameters != null)
			href.append('[').append(parameters).append(']');
		return href.toString();
	}

	private static Pattern pattern = Pattern.compile("^(.*)/([^/]*)$");

	/**
	 * 从URI中分离出action名称 并返回这个action名称是否在列表里
	 * 
	 * @param urilist
	 * @param uri
	 * @return
	 */
	public static final boolean contains(List<String> urilist, String uri) {
		String action;
		Matcher matcher = pattern.matcher(uri);
		if (matcher.matches() && matcher.groupCount() == 2) {
			action = matcher.group(2);
		} else {
			action = uri;
		}
		for (String str : urilist) {
			if (str.equals(action)) {
				return true;
			}
		}
		return false;
	}
}
