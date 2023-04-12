package com.m2m.trello.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class CardDto {

	private int cardId;
	
	private int todoId;
	
	private int seq;
	
	private String cardTitle;
	
	private String description;
	
	private Date regDate;

	private List<AttachDto> fileList;

}
