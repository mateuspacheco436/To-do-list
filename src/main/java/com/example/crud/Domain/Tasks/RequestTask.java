package com.example.crud.Domain.Tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RequestTask(
        @NotBlank(message = "O titulo e obrigatorio")
        @Size(max = 100, message = "O titulo deve ter no maximo 100 caracteres") String titulo,
        @Size(max = 500, message = "A descricao deve ter no maximo 500 caracteres") String descricao,
        TaskPriority prioridade,
        LocalDate dataLimite) {
}
