package com.example.todolist.repository;

import com.example.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByNome(String nome);

    boolean existsByNomeAndDescricao(String nome, String descricao);
}
