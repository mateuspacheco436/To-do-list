package com.example.crud.Domain.Tasks;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.crud.Domain.User.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Table(name = "tarefas")
@Entity(name = "tarefas")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    private boolean concluida = false;

    @Enumerated(EnumType.STRING)
    private TaskPriority prioridade = TaskPriority.MEDIA;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDate dataLimite;

    // Dono da tarefa: cada usuario so enxerga as proprias
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
