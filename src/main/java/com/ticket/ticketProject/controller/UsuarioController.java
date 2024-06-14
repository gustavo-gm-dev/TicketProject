package com.ticket.ticketProject.controller;

import com.ticket.ticketProject.model.dto.usuario.CadastrarUsuarioDTO;
import com.ticket.ticketProject.model.dto.usuario.UsuarioDTO;
import com.ticket.ticketProject.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarUsuarioDTO cadastrarUsuarioDTO, UriComponentsBuilder uriBuilder) {
        UsuarioDTO usuarioDTO = service.cadastrarUsuario(cadastrarUsuarioDTO);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioDTO.id()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @GetMapping("/{id}")
    public UsuarioDTO usuarioFindById(@PathVariable("id")Long id) {
        return service.usuarioFindById(id);
    }

}