package com.ticket.ticketProject.service;

import com.ticket.ticketProject.model.dto.usuario.CadastrarUsuarioDTO;
import com.ticket.ticketProject.model.Usuario;
import com.ticket.ticketProject.model.dto.usuario.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO cadastrarUsuario(CadastrarUsuarioDTO cadastrarUsuarioDTO);

    UsuarioDTO usuarioFindById(Long id);
}