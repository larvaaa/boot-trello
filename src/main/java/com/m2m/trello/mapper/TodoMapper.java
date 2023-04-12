package com.m2m.trello.mapper;

import com.m2m.trello.dto.AttachDto;
import com.m2m.trello.dto.CardDto;
import com.m2m.trello.dto.TodoDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TodoMapper {

	public int insertTodo(TodoDto todoDto);
	
	public int updateTodo(TodoDto todoDto);
	
	public int deleteTodo(int todoId);
	
	public List<TodoDto> selectTodoList();
	
	public TodoDto selectTodo(int todoId);

	public List<CardDto> selectCardList(int todoId);

	public int insertCard(CardDto cardDto);

	public void insertAttach(AttachDto attachDto);

	public CardDto selectCard(int cardId);

	public List<AttachDto> selectAttachList(int cardId);

	public int updateCard(CardDto cardDto);

	public int deleteAttach(int attachId);

	public AttachDto selectAttach(int attachId);

	public int deleteCard(int cardId);

}
