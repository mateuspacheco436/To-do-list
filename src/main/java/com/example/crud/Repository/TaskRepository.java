package com.example.crud.Repository;

import com.example.crud.Domain.Tasks.Task;
import com.example.crud.Domain.User.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {

    // pendentes primeiro, depois as mais recentes
    List<Task> findByUsuarioOrderByConcluidaAscDataCriacaoDesc(Usuario usuario);

    Optional<Task> findByIdAndUsuario(String id, Usuario usuario);
}
