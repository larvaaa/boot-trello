package com.m2m.trello.service;

import com.m2m.trello.dto.AttachDto;
import com.m2m.trello.dto.CardDto;
import com.m2m.trello.dto.TodoDto;
import com.m2m.trello.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {

//    private final Environment env;

    @Value("${upload.path}")
    private String uploadPath;

    private final TodoMapper todoMapper;

    public int createTodo(TodoDto todoDto) {

        return todoMapper.insertTodo(todoDto);
    }

    public List<TodoDto> getTodoList() {

        List<TodoDto> todoList = todoMapper.selectTodoList();

        for(int i = 0; i < todoList.size(); i++) {

            int todoId = todoList.get(i).getTodoId();
            List<CardDto> cardList = todoMapper.selectCardList(todoId);
            todoList.get(i).setCards(cardList);

        }

        return todoList;

    }

    public void updateTodo(TodoDto todoDto) {
        todoMapper.updateTodo(todoDto);
    }

    @Transactional
    public int createCard(CardDto cardDto, MultipartFile[] files) throws Exception {
        todoMapper.insertCard(cardDto);

        int cardId = cardDto.getCardId();
        if(cardId > 0 && !(files == null || files.length == 0)) {

            for(MultipartFile file : files) {

                String origialFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + origialFileName.substring(origialFileName.lastIndexOf("."));

                file.transferTo(new File(uploadPath + "/" + fileName));

                AttachDto attachDto = new AttachDto();
                attachDto.setCardId(cardId);
                attachDto.setOriFileName(origialFileName);
                attachDto.setFileName(fileName);
                attachDto.setFilePath(uploadPath);
                attachDto.setFileSize(file.getSize());

                todoMapper.insertAttach(attachDto);
            }

        }

        return cardId;
    }

    public CardDto selectCard(int cardId) {
        CardDto cardDto = todoMapper.selectCard(cardId);

        cardDto.setFileList(todoMapper.selectAttachList(cardId));
        return cardDto;
    }

    @Transactional
    public void updateCard(CardDto cardDto, MultipartFile[] files, int[] removeFiles)  throws Exception {

        int result = todoMapper.updateCard(cardDto);

        if(result > 0) {
            int cnt = 0;
            for(int attachId : removeFiles) {

                AttachDto findAttach = todoMapper.selectAttach(attachId);
                File file = new File(uploadPath + "/" + findAttach.getFileName());
                file.delete();
                cnt += todoMapper.deleteAttach(attachId);

            }

            if(cnt == removeFiles.length && !(files == null || files.length == 0)) {

                for(MultipartFile file : files) {

                    String origialFileName = file.getOriginalFilename();
                    String fileName = UUID.randomUUID().toString() + origialFileName.substring(origialFileName.lastIndexOf("."));

                    file.transferTo(new File(uploadPath + "/" + fileName));

                    AttachDto attachDto = new AttachDto();
                    attachDto.setCardId(cardDto.getCardId());
                    attachDto.setOriFileName(origialFileName);
                    attachDto.setFileName(fileName);
                    attachDto.setFilePath(uploadPath);
                    attachDto.setFileSize(file.getSize());

                    todoMapper.insertAttach(attachDto);
                }

            }

        }

    }

    @Transactional
    public int removeCard(int cardId) {

        List<AttachDto> findAttachList = todoMapper.selectAttachList(cardId);

        for(AttachDto dto : findAttachList) {

            String fileName = dto.getFileName();

            File file = new File(uploadPath + "/" + fileName);
            file.delete();

            todoMapper.deleteAttach(dto.getAttachId());
        }

        return todoMapper.deleteCard(cardId);

    }

    @Transactional
    public int removeTodo(int todoId) {

        List<CardDto> findCardList = todoMapper.selectCardList(todoId);

        for(CardDto dto : findCardList) {
            removeCard(dto.getCardId());
        }


        return todoMapper.deleteTodo(todoId);

    }
}
