package com.example.todolist.controller;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskServic) {
        this.taskService = taskServic;
    }

    @PostMapping
    public Task criar(@RequestBody TaskDTO dto) {
        return taskService.criarTask(dto);
    }

    @GetMapping
    public List<Task> listar(){
        return taskService.listarTasks();
    }

    @PatchMapping("/{id}")
    public Task atualizar(@PathVariable Long id, @RequestBody Map<String, String> corpo){
        return taskService.atualizarTask(id, corpo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        taskService.deletarTask(id);
    }
}



