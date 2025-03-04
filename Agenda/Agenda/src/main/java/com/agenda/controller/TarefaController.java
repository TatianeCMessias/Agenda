package com.agenda.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.demain.dto.TarefaDTO;
import com.agenda.demain.model.Tarefa;
import com.agenda.service.TarefaService;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;
    
    @GetMapping
    public ResponseEntity<List<TarefaDTO>> listarTodasTarefas() {
        List<Tarefa> tarefas = tarefaService.findAllTarefa();
        
        List<TarefaDTO> tarefasDTO = tarefas.stream()
            .map(tarefa -> {
                TarefaDTO dto = new TarefaDTO();
                dto.setTitulo(tarefa.getTitulo());
                dto.setDescricao(tarefa.getDescricao());
                dto.setConcluida(tarefa.getConcluida());
                return dto;
            })
            .collect(Collectors.toList());
        
        return new ResponseEntity<>(tarefasDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarTarefaPorId(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.findByTarefaId(id);
            TarefaDTO tarefaDTO = new TarefaDTO();
            tarefaDTO.setTitulo(tarefa.getTitulo());
            tarefaDTO.setDescricao(tarefa.getDescricao());
            tarefaDTO.setConcluida(tarefa.getConcluida());
            
            return new ResponseEntity<>(tarefaDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Tarefa> criarTarefa(@PathVariable Long usuarioId, @RequestBody TarefaDTO tarefaDTO) {
        try {

            Tarefa novaTarefa = new Tarefa();
            novaTarefa.setTitulo(tarefaDTO.getTitulo());
            novaTarefa.setDescricao(tarefaDTO.getDescricao());
            novaTarefa.setConcluida(tarefaDTO.getConcluida());


            Tarefa tarefaCriada = tarefaService.criarTarefa(usuarioId, novaTarefa);

            return new ResponseEntity<>(tarefaCriada, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{tarefaId}")
    public ResponseEntity<TarefaDTO> editarTarefa(@PathVariable Long tarefaId, @RequestBody TarefaDTO tarefaDTO) {
        try {
            Tarefa tarefaAtualizada = new Tarefa();
            tarefaAtualizada.setTitulo(tarefaDTO.getTitulo());
            tarefaAtualizada.setDescricao(tarefaDTO.getDescricao());
            tarefaAtualizada.setConcluida(tarefaDTO.getConcluida());

            Tarefa tarefaEditada = tarefaService.editarTarefa(tarefaId, tarefaAtualizada);

            TarefaDTO responseDTO = new TarefaDTO();
            responseDTO.setTitulo(tarefaEditada.getTitulo());
            responseDTO.setDescricao(tarefaEditada.getDescricao());
            responseDTO.setConcluida(tarefaEditada.getConcluida());

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long tarefaId) {
        try {
            tarefaService.excluirTarefa(tarefaId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}