package com.ticket.ticketProject.service;

import com.ticket.ticketProject.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario cadastrarUsuario(Usuario usuario);

    List<Usuario> listarTodos();
}