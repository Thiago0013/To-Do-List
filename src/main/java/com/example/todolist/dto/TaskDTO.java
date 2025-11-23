package com.example.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TaskDTO {
    @NotBlank
    private String nome;

    @Size(max = 255)
    private String descricao;
    private boolean concluido;
    private LocalDate prazo;

    public boolean isConcluido(){
        return concluido;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }
}
