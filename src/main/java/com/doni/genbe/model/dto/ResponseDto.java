package com.doni.genbe.model.dto;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("ResponseDtoFilter")
public class ResponseDto {
	private String status;
	private String message;
	
	private GetDto data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GetDto getData() {
		return data;
	}

	public void setData(GetDto data) {
		this.data = data;
	}
	
}
