package com.example.crud.Controller;

import com.example.crud.Domain.Tasks.RequestTask;
import com.example.crud.Domain.Tasks.Task;
import com.example.crud.Domain.Tasks.TaskPriority;
import com.example.crud.Domain.Tasks.TaskResponse;
import com.example.crud.Domain.User.Usuario;
import com.example.crud.Repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/V1/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TaskResponse> list(@AuthenticationPrincipal Usuario usuario) {
        return repository.findByUsuarioOrderByConcluidaAscDataCriacaoDesc(usuario)
                .stream().map(TaskResponse::from).toList();
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@AuthenticationPrincipal Usuario usuario,
                                               @RequestBody @Valid RequestTask data) {
        Task task = new Task();
        task.setUsuario(usuario);
        apply(task, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponse.from(repository.save(task)));
    }

    @PutMapping("/{id}")
    public TaskResponse update(@AuthenticationPrincipal Usuario usuario, @PathVariable String id,
                               @RequestBody @Valid RequestTask data) {
        Task task = findOrThrow(id, usuario);
        apply(task, data);
        return TaskResponse.from(repository.save(task));
    }

    /** Alterna entre pendente e concluida. */
    @PatchMapping("/{id}/toggle")
    public TaskResponse toggle(@AuthenticationPrincipal Usuario usuario, @PathVariable String id) {
        Task task = findOrThrow(id, usuario);
        task.setConcluida(!task.isConcluida());
        return TaskResponse.from(repository.save(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Usuario usuario, @PathVariable String id) {
        repository.delete(findOrThrow(id, usuario));
        return ResponseEntity.noContent().build();
    }

    private void apply(Task task, RequestTask data) {
        task.setTitulo(data.titulo().trim());
        task.setDescricao(data.descricao() == null || data.descricao().isBlank() ? null : data.descricao().trim());
        task.setPrioridade(data.prioridade() == null ? TaskPriority.MEDIA : data.prioridade());
        task.setDataLimite(data.dataLimite());
    }

    // Tarefa de outro usuario responde 404, sem revelar que ela existe
    private Task findOrThrow(String id, Usuario usuario) {
        return repository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa nao encontrada"));
    }
}
