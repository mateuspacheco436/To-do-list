package com.example.crud.Domain.Tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(String id, String titulo, String descricao, boolean concluida,
                           TaskPriority prioridade, LocalDateTime dataCriacao, LocalDate dataLimite) {

    public static TaskResponse from(Task t) {
        return new TaskResponse(t.getId(), t.getTitulo(), t.getDescricao(), t.isConcluida(),
                t.getPrioridade(), t.getDataCriacao(), t.getDataLimite());
    }
}
