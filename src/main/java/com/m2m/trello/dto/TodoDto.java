package com.m2m.trello.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class TodoDto {

	private int todoId;

	private int memberId;

	private String todoTitle;

	private Date regDate;

	private List<CardDto> cards;
}
