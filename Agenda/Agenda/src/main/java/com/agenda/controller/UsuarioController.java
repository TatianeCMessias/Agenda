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

import com.agenda.demain.dto.UsuarioDTO;
import com.agenda.demain.model.Usuario;
import com.agenda.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
            .map(usuario -> {
                UsuarioDTO dto = new UsuarioDTO();
                dto.setNomeUsuario(usuario.getNomeUsuario());
                dto.setSenha(usuario.getSenha());
                return dto;
            })
            .collect(Collectors.toList());
        
        return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> listarUsuarioId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.listarUsuarioId(id);

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNomeUsuario(usuario.getNomeUsuario());
            usuarioDTO.setSenha(usuario.getSenha());

            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {

            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(usuarioDTO.getNomeUsuario());
            usuario.setSenha(usuarioDTO.getSenha());


            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);

            UsuarioDTO usuarioDTORegistrado = new UsuarioDTO(
                usuarioRegistrado.getNomeUsuario(),
                usuarioRegistrado.getSenha()
            );

            return new ResponseEntity<>(usuarioDTORegistrado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {

            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setNomeUsuario(usuarioDTO.getNomeUsuario());
            usuario.setSenha(usuarioDTO.getSenha());


            Usuario usuarioEditado = usuarioService.editarUsuario(id, usuario);


            UsuarioDTO usuarioDTOEditado = new UsuarioDTO(
                usuarioEditado.getNomeUsuario(),
                usuarioEditado.getSenha()
            );

            return new ResponseEntity<>(usuarioDTOEditado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarUsuario(@PathVariable Long id) {
        try {
            usuarioService.apagarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // HTTP 204
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
