package com.example.todolist.controller;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> criar(@RequestBody TaskDTO dto) {
        var novaTask = taskService.criarTask(dto);
        return ResponseEntity.ok(novaTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> listar(){
        var tasks = taskService.listarTasks();
        return ResponseEntity.ok(tasks);
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