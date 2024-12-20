package com.demo.netty.param;


import com.alibaba.fastjson.JSONObject;

public class RequestParam {
	
	private String command;
	
	private Object content;
	
	private  long id;
	
	public static String message(String content) {
		RequestParam param = new RequestParam();
		param.setContent(content);
		param.setId(System.currentTimeMillis());
		param.setCommand(MessageType.TEXT.name());
		return JSONObject.toJSONString(param);
	}
	
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	
	
	public static enum MessageType {
		COMMAND,
		TEXT,
		IMAGE,
		FILE,
		AUDIO,
		VIDEO,
		LINK,
		LOCATION,
		EVENT,
		OTHER;
	}
	
	
	
	
}
