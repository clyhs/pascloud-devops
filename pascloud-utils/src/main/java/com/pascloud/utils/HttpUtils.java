package com.pascloud.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.vo.result.ResultCommon;

public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String httpGetTool(String urlPath, Map<String, NameValuePair> nvps
			/*** List<NameValuePair> nvps **/
			,List<NameValuePair> headers) {
		HttpClient http = new DefaultHttpClient();

		http.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);

		StringBuilder url = new StringBuilder();

		url.append(urlPath);
		if (null != nvps && nvps.size() > 0) {
			url.append("?");
			try {
				Iterator<String> it = nvps.keySet().iterator();
				NameValuePair nvp = null;
				while (it.hasNext()) {
					nvp = nvps.get(it.next());
					if (nvp != null) {
						url.append(nvp.getName()).append("=").append(URLEncoder.encode(nvp.getValue(), "utf-8"))
								.append("&");
					}
					nvp = null;
				}
			} catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				logger.error("http error", e);

				http.getConnectionManager().shutdown();
			} finally {
				// http.getConnectionManager().shutdown();
			}

			if (url.toString().endsWith("&")) {
				url.deleteCharAt(url.lastIndexOf("&"));
			}
		}
	    System.out.println(">>>"+url.toString());
		HttpGet httpget = new HttpGet(url.toString());
		StringBuffer sb = new StringBuffer();
		HttpProtocolParams.setUseExpectContinue(http.getParams(), false);
		HttpResponse response = null;
		int code = 200;
		try {
			setHeader(httpget, headers);
			response = http.execute(httpget);
			HttpEntity entity = response.getEntity();
			code = response.getStatusLine().getStatusCode();
			logger.info("http response code = " + code);
			// 显示结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			if (reader != null) {
				reader.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("http error!", e);
			http.getConnectionManager().shutdown();
		} finally {
			// http.getConnectionManager().shutdown();
		}
		logger.debug(sb.toString());
		if (code != 200) {
			logger.error("http error[" + code + "]", sb.toString());
			return "{}";
		}
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	public static String httpPostTool(String url, Map<String, NameValuePair> nvps
			/*** List<NameValuePair> nvps */
			,List<NameValuePair> header) {
		HttpClient http = new DefaultHttpClient();
		http.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity entityform;
		StringBuffer sb = new StringBuffer();
		List<NameValuePair> nvpslist = new ArrayList<NameValuePair>();
		Iterator<String> it = nvps.keySet().iterator();
		// System.out.println("************************");
		while (it.hasNext()) {

			String v1 = it.next();
			// System.out.println("k:"+v1+" ,v:"+nvps.get(v1));
			nvpslist.add(nvps.get(v1));

		}
		// System.out.println("************************");
		int code = 200;
		try {
			entityform = new UrlEncodedFormEntity(nvpslist, HTTP.UTF_8);

			setHeader(httpPost, header);
			httpPost.setEntity(entityform);
			HttpResponse response = http.execute(httpPost);
			HttpEntity entity = response.getEntity();
			code = response.getStatusLine().getStatusCode();
			// logger.info("http response code = " + code);
			HttpProtocolParams.setUseExpectContinue(http.getParams(), false);
			// 显示结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			logger.info("http error!", e.getMessage());
		} finally {
			http.getConnectionManager().shutdown();
		}
		// System.out.println("******************************");
		// System.out.println(sb.toString()+"---"+code);
		// System.out.println("******************************");
		logger.debug(sb.toString());
		if (code != 200) {
			logger.error("http error[" + code + "]", sb.toString());
			return "{}" + "---" + code;
		}
		if (sb.toString() == null) {
			logger.info("服务器连接不上");
			return "{}" + "---" + code;
		} else {
			return sb.toString() + "---" + code;
		}

		// return sb.toString()+"*"+code;

	}

	public static void setHeader(HttpRequestBase request, List<NameValuePair> headers) {
		for (NameValuePair nvp : headers) {
			request.setHeader(nvp.getName(), nvp.getValue());
		}
	}

	public static void main(String[] args) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
        String r= HttpUtils.httpGetTool("http://192.168.0.7:8311/module/common/v100/health.json",null,params);
		System.out.println(r);
		//ResultCommon result = new ObjectMapper().readValue(r, new TypeReference<BaseRespone>() {
		//});
	}

}