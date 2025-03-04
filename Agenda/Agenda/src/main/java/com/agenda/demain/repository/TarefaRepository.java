package com.agenda.demain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agenda.demain.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}
