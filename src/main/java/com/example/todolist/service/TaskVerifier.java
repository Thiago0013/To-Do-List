package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TaskVerifier {
    protected final TaskRepository repository;

    public TaskVerifier(TaskRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void verificarTask(){
        List<Task> tasks = repository.findAll()
                .stream().filter(t -> !t.isConcluido())
                .toList();

        tasks.forEach(task ->{
            if(task.getPrazo() != null && !task.isConcluido()){
                if(LocalDate.now().isAfter(task.getPrazo())  && !task.isAtrasada()){
                    task.setAtrasada(true);
                    repository.save(task);
                }
            }
        });
    }
}
