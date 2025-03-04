package com.agenda.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.demain.model.Tarefa;
import com.agenda.demain.model.Usuario;
import com.agenda.demain.repository.TarefaRepository;
import com.agenda.demain.repository.UsuarioRepository;
import com.agenda.service.TarefaService;

@Service
public class TarefaServiceImpl implements TarefaService{
	
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Tarefa> findAllTarefa() {
    	return tarefaRepository.findAll();
    }
    
	@Override
	public Tarefa findByTarefaId(Long id) {
		return tarefaRepository.findById(id).orElseThrow(NoSuchElementException::new);
	}

    // Criar uma nova Tarefa
    @Override
    public Tarefa criarTarefa(Long usuarioId, Tarefa tarefa) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        tarefa.setUsuario(usuario);
        tarefa.setDataCriacao(LocalDateTime.now()); // Define a data de criação
        tarefa.setDataAtualizacao(LocalDateTime.now()); // Define a data de atualização


        return tarefaRepository.save(tarefa);
    }


    @Override
    public Tarefa editarTarefa(Long tarefaId, Tarefa tarefaAtualizada) {

        Tarefa tarefaExistente = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));


        tarefaExistente.setTitulo(tarefaAtualizada.getTitulo());
        tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
        tarefaExistente.setConcluida(tarefaAtualizada.getConcluida());
        tarefaExistente.setDataAtualizacao(LocalDateTime.now()); // Atualiza a data de atualização


        return tarefaRepository.save(tarefaExistente);
    }


    @Override
    public void excluirTarefa(Long tarefaId) {

        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));


        tarefaRepository.delete(tarefa);
    }
}
