package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    private User getUserLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public Task criarTask(TaskDTO dto) {
        if(repository.existsByNome(dto.getNome())){
            throw new RuntimeException("ERRO: Já existe tarefa com esse nome!");
        }

        User user = getUserLogado();

        Task task = new Task();
        task.setNome(dto.getNome());
        task.setDescricao(dto.getDescricao());
        task.setPrazo(dto.getPrazo());
        task.setAtrasada(false);

        task.setUser(user);

        return repository.save(task);
    }

    public List<Task> listarTasks() {
        User user = getUserLogado();
        return repository.findByUser(user);
    }

    public Task atualizarTask(Long id, Map<String, String> corpo){
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id não encontrado!"));

        User userLogado = getUserLogado();
        if(!task.getUser().getId().equals(userLogado.getId())){
            throw new RuntimeException("Acesso Negado: Essa tarefa não é sua!");
        }

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
            }
        });
        return repository.save(task);
    }

    public void deletarTask(Long id){
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontrado!"));

        User userLogado = getUserLogado();
        if(!task.getUser().getId().equals(userLogado.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode deletar tarefas de outros!");
        }

        repository.deleteById(id);
    }
}