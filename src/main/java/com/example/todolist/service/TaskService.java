package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

@Service
public class TaskService {

    protected final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task criarTask(TaskDTO dto) {
        if(repository.existsByNome(dto.getNome())){
            throw new RuntimeException("ERRO: Já existe tarefa com esse nome!");
        }

        Task task = new Task();
        task.setNome(dto.getNome());
        task.setDescricao(dto.getDescricao());
        task.setPrazo(dto.getPrazo());
        task.setAtrasada(false);
        return repository.save(task);
    }

    public List<Task> listarTasks() {
        return repository.findAll();
    }

    public Task atualizarTask(Long id, Map<String, String> corpo){
        Task task = repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));

        corpo.forEach((nome, valor) -> {
            switch (nome) {
                case "nome":
                    task.setNome(valor);
                    break;
                case "descricao":
                    task.setDescricao(valor);
                    break;
                case "concluido":
                    boolean status = Boolean.parseBoolean(valor);
                    task.setConcluido(status);

                    if (status) {
                        task.setDataConclusao(LocalDate.now());
                    } else {
                        task.setDataConclusao(null);
                    }
                    break;
                default:
                    throw new RuntimeException("ERRO de entrada de dados!");
            }
        });
        return repository.save(task);
    }

    public void deletarTask(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontado!");
        }

        repository.deleteById(id);
    }
}
