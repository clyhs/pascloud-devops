package com.pascloud.core.mina.code;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.http.api.HttpResponse;
import org.apache.mina.http.api.HttpStatus;
import org.apache.mina.http.api.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HttpResponseImpl implements HttpResponse {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpResponseImpl.class);

    private final HashMap<String, String> headers = new HashMap<>();		//头信息

    private final HttpVersion version = HttpVersion.HTTP_1_1;
    private HttpStatus status = HttpStatus.CLIENT_ERROR_FORBIDDEN;
    private final StringBuffer bodyStringBuffer;	//内容
    private byte[] body;	//内容

    public HttpResponseImpl() {
        headers.put("Server", "HttpServer (" + "Mina 2.0.13" + ')');
        headers.put("Cache-Control", "private");
        headers.put("Content-Type", "text/html; charset=UTF-8");
        headers.put("Connection", "keep-alive");
        headers.put("Keep-Alive", "500");
        headers.put("Date", new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(new Date()));
        headers.put("Last-Modified", new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(new Date()));
        status = HttpStatus.SUCCESS_OK;
        bodyStringBuffer = new StringBuffer();
    }

    public void setContentType(String contentType) {
        headers.put("Content-Type", contentType);
    }
    
    public HttpResponseImpl appendBody(String s) {
        this.bodyStringBuffer.append(s);
        return this;
    }
    
    /**
     * 内容长度
     * @return
     */
    public int bodyLength(){
        return bodyStringBuffer.length();
    }

    public byte[] getBody() {
        try {
            if (body == null) {
                body = this.bodyStringBuffer.toString().getBytes("utf-8");
            }
        } catch (IOException ex) {
            LOG.error("getBody", ex);
        }
        return body;
    }
    
	@Override
	public HttpVersion getProtocolVersion() {
		// TODO Auto-generated method stub
		return version;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return headers.get("Content-type");
	}

	@Override
	public boolean isKeepAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return headers.get(name);
	}

	@Override
	public boolean containsHeader(String name) {
		// TODO Auto-generated method stub
		return headers.containsKey(name);
	}

	@Override
	public Map<String, String> getHeaders() {
		// TODO Auto-generated method stub
		return headers;
	}

	@Override
	public HttpStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP RESPONSE STATUS: ").append(status).append('\n');
        sb.append("VERSION: ").append(version).append('\n');

        sb.append("-- HEADER --- \n");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(':').append(entry.getValue()).append('\n');
        }

        return sb.toString();
    }
	
	/**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
