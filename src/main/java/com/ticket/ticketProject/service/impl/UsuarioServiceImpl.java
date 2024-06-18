package com.ticket.ticketProject.service.impl;

import com.ticket.ticketProject.model.dto.usuario.CadastrarUsuarioDTO;
import com.ticket.ticketProject.model.Usuario;
import com.ticket.ticketProject.model.dto.usuario.UsuarioDTO;
import com.ticket.ticketProject.model.mapper.UsuarioMapper;
import com.ticket.ticketProject.repository.UsuarioRepository;
import com.ticket.ticketProject.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDTO cadastrarUsuario(CadastrarUsuarioDTO dto) {
        Usuario usuario = usuarioMapper.cadastrarUsuarioDTOToEntity(dto);
        usuario = repository.save(usuario);
        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    @Override
    public UsuarioDTO usuarioFindById(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        return usuario.map(usuarioMapper::usuarioToUsuarioDto).orElse(null);
    }
}