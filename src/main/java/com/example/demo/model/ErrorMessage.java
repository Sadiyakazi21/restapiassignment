package com.example.demo.model;

import java.util.Date;

public class ErrorMessage {
  
	private Date timestamp;
	private String  message;
	
	public ErrorMessage() {
		
	}
    public ErrorMessage( Date timestamp, String  message) {
		this.timestamp = timestamp;
		this.message = message;
	}
	public ErrorMessage(String string, String string2) {
		// TODO Auto-generated constructor stub
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
