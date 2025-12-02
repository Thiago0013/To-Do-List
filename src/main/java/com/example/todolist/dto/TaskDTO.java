package com.example.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TaskDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Size(max = 255, message = "A descrição é muito longa")
    private String descricao;

    private boolean concluido;
    private LocalDate prazo;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConcluido(){
        return concluido;
    }

    public LocalDate getPrazo() {
        return prazo;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }
}