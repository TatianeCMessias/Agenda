package com.agenda.service;

import java.util.List;

import com.agenda.demain.model.Usuario;

public interface UsuarioService {
	
    List<Usuario> listarUsuarios();
	
    Usuario listarUsuarioId(Long id);

    Usuario registrarUsuario(Usuario usuarioToCreate);

    Usuario editarUsuario(Long id, Usuario updatedUsuario);

    void apagarUsuario(Long id);
}
