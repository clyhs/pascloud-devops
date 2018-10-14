package com.pascloud.gate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class KeystoreTestClient {
	/**
	 * name:KeystoreTestClient author:suju
	 */
	public static void main(String[] args) throws Exception {
		String key = "c:/client";
		KeyStore keystore = KeyStore.getInstance("JKS"); // 创建一个keystore来管理密钥库
		keystore.load(new FileInputStream(key), "123456".toCharArray());
		// 创建jkd密钥访问库
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(keystore); // 验证数据，可以不传入key密码
		// 创建TrustManagerFactory,管理授权证书
		SSLContext sslc = SSLContext.getInstance("SSLv3");
		// 构造SSL环境，指定SSL版本为3.0，也可以使用TLSv1，但是SSLv3更加常用。
		sslc.init(null, tmf.getTrustManagers(), null);
		// KeyManager[] 第一个参数是授权的密钥管理器，用来授权验证。第二个是被授权的证书管理器，
		// 用来验证服务器端的证书。只验证服务器数据，第一个管理器可以为null
		// 构造ssl环境

		SSLSocketFactory sslfactory = sslc.getSocketFactory();
		SSLSocket socket = (SSLSocket) sslfactory.createSocket("127.0.0.1", 9999);
		// 创建serversocket通过传输数据来验证授权

		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		os.write("client".getBytes());
		byte[] buf = new byte[1024];
		int len = is.read(buf);
		System.out.println(new String(buf));
		os.close();
		is.close();
	}
}
