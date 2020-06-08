package com.example.restservice;

public class Response {

	private final String ip;
	private final String content;

	public Response(String ip, String content) {
		this.ip = ip;
		this.content = content;
	}

	public String getId() {
		return ip;
	}

	public String getContent() {
		return content;
	}
}
