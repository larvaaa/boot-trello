package com.m2m.trello.controller;

import com.m2m.trello.dto.CardDto;
import com.m2m.trello.dto.TodoDto;
import com.m2m.trello.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

    Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    @PostMapping("/todo")
    public HashMap<String, String> createTodo(@RequestBody TodoDto todoDto) {

        HashMap<String,String> resultMap = new HashMap<>();

        try {
            int todoId = todoService.createTodo(todoDto);

            resultMap.put("todoId","todoId");
            resultMap.put("resultCd","S");

        } catch (Exception e) {
            resultMap.put("resultCd","F");
        }

        return resultMap;
    }
    
    //투두리스트 조회
    @GetMapping("/todo")
    public HashMap<String, Object> getTodoList() {

        HashMap<String, Object> resultMap = new HashMap<>();
        List<TodoDto> list = new ArrayList<>();

        try {
            list = todoService.getTodoList();
            resultMap.put("todoList",list);
            resultMap.put("resultCd","S");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resultCd","F");
        }

        return resultMap;
    }

    @GetMapping("/card/{cardId}")
    public HashMap<String,Object> getCard(@PathVariable("cardId") int cardId) {

        HashMap<String,Object> resultMap = new HashMap<>();

        try {
            CardDto findCard = todoService.selectCard(cardId);
            resultMap.put("card", findCard);
            resultMap.put("resultCd", "S");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resultCd", "F");
        }
        return resultMap;
    }



    @PatchMapping("/todo/{todoId}")
    public HashMap<String, String> updateTodo(
            @PathVariable int todoId,
            @RequestBody TodoDto todoDto) {

        HashMap<String,String> resultMap = new HashMap<>();
        try {
            todoDto.setTodoId(todoId);
            todoService.updateTodo(todoDto);
            resultMap.put("resultCd","S");
        } catch (Exception e) {
            resultMap.put("resultCd","F");
        }
        return resultMap;
    }

    @PostMapping("/card")
    public HashMap<String,String> createCard(
            @ModelAttribute CardDto cardDto,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {

        HashMap<String,String> resultMap = new HashMap<>();

        try {
            todoService.createCard(cardDto,files);
            resultMap.put("resultCd","S");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resultCd","F");
        }
        return resultMap;
    }

    @PatchMapping("card/{cardId}")
    public HashMap<String,String> updateCard(
            @ModelAttribute CardDto cardDto,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "removeFiles",required = false) int[] removeFiles) {

        HashMap<String,String> resultMap = new HashMap<>();

        try {
            todoService.updateCard(cardDto, files, removeFiles);
            resultMap.put("resultCd","S");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resultCd","F");
        }
        return resultMap;
    }

    @DeleteMapping("/card/{cardId}")
    public HashMap<String,String> removeCard(@PathVariable int cardId) {

        HashMap<String,String> resultMap = new HashMap<>();

        try {
            todoService.removeCard(cardId);
            resultMap.put("resultCd","S");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resultCd","F");
        }
        return resultMap;
    }

    @DeleteMapping("/todo/{todoId}")
    public HashMap<String,String> removeTodo(@PathVariable int todoId) {

        HashMap<String,String> resultMap = new HashMap<>();

        try {
            todoService.removeTodo(todoId);
            resultMap.put("resultCd","S");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resultCd","F");
        }
        return resultMap;
    }
}
