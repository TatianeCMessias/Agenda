package com.agenda.service;

import java.util.List;

import com.agenda.demain.model.Tarefa;

public interface TarefaService {
	
	List<Tarefa> findAllTarefa()
;	
    Tarefa findByTarefaId(Long usuarioId);
    
    Tarefa criarTarefa(Long usuarioId, Tarefa tarefa);
    
    Tarefa editarTarefa(Long tarefaId, Tarefa tarefaAtualizada);
    
    void excluirTarefa(Long tarefaId);
}