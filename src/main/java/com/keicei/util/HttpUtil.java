package com.keicei.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
/**
 * 
 * @author ZHANGYAN
 *
 */
public class HttpUtil {

	/**
	 * 可以对参数进行编码
	 * 
	 * @param url
	 * @param parameters
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static final String get(String url, Map<String, String> parameters)
			throws Exception {
		return get(url, parameters, "UTF-8");
	}

	public static String get(String url, Map<String, String> parameters,
			String charset) throws Exception {

		StringBuilder myurl = new StringBuilder();
		myurl.append(url);

		if (parameters != null && parameters.size() > 0) {
			myurl.append('?');
			boolean first = true;
			Set<Entry<String, String>> set = parameters.entrySet();
			String value;
			for (Entry<String, String> entry : set) {
				if (first) {
					first = false;
				} else {
					myurl.append('&');
				}
				myurl.append(entry.getKey());
				myurl.append('=');
				value = entry.getValue();
				if (value != null && value.length() > 0) {
					myurl.append(URLEncoder.encode(value, charset));
				}
			}
		}

		return get(myurl.toString(), charset);

	}

	public static String get(String url, String charset) throws Exception {
		GetMethod m = new GetMethod(url);
		StringBuilder rsp = new StringBuilder();
		HttpClient httpClient = null;
		try {
			m.setRequestHeader("Connection", "close");
			httpClient = new HttpClient();
			if (httpClient.executeMethod(m) == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						m.getResponseBodyAsStream(), charset));
				boolean first = true;
				String line;
				try {
					while ((line = br.readLine()) != null) {
						if (first) {
							first = false;
						} else {
							rsp.append('\n');
						}
						rsp.append(line);
					}
				} catch (Exception e) {
					throw e;
				} finally {
					try {
						br.close();
					} catch (Exception e) {
					}
				}
				return rsp.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				m.releaseConnection();
				SimpleHttpConnectionManager simpleHttpConnectionManager = (SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager();
				if (simpleHttpConnectionManager != null) {
					simpleHttpConnectionManager.shutdown();
				}
			} catch (Exception e) {
			}
		}
	}

	public static String get(String url) throws Exception {
		return get(url, "UTF-8");
	}
}
