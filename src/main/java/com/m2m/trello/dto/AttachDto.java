package com.m2m.trello.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttachDto {

	private int attachId;
	
	private int cardId;
	
	private String fileName;
	
	private String oriFileName;
	
	private long fileSize;
	
	private String filePath;
	
	private Date regDate;
}
