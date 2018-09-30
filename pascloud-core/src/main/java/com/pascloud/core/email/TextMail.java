package com.pascloud.core.email;

public class TextMail implements Runnable {

	/** 标题 */
	private String title;
	/** 内容 */
	private String content;

	public TextMail(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}