package com.qingting.platform.message;

import java.io.Serializable;
import java.util.Calendar;

public class MessageServer<T> implements Serializable{
	
	private static final long serialVersionUID = 5884803621320684853L;
	
	/**
	 * 消息类型编码
	 */
	private String type;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息数据
	 */
	private T data;
	/**
	 * 消息产生时间
	 */
	private Calendar time;
	
	public MessageServer() {
		super();
	}

	public MessageServer(String type, String title, String content, T data, Calendar time) {
		super();
		this.type = type;
		this.title = title;
		this.content = content;
		this.data = data;
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "MessageServer [type=" + type + ", title=" + title + ", content=" + content + ", data=" + data
				+ ", time=" + time + "]";
	}
	
	
}
