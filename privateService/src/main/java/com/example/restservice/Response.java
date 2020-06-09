package com.example.restservice;

public class Response {

	private final String id;
	private final String content;

	public Response(String id, String content) {
		this.id = id;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
