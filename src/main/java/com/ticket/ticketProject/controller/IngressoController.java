package com.ticket.ticketProject.controller;

import com.ticket.ticketProject.model.dto.ingresso.AtualizarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.CadastrarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.IngressoDTO;
import com.ticket.ticketProject.service.IngressoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    @Autowired
    private IngressoService ingresso;

    @GetMapping
    public List<IngressoDTO> getAllIngressos() {
        return ingresso.getAllIngressos();
    }

    @GetMapping("{id}")
    public IngressoDTO getIngressoById(@PathVariable Long id){
        return ingresso.getIngressoById(id);
    }

    @PostMapping("/cadastrar/{idUsuario}")
    @Transactional
    public ResponseEntity cadastrar(@PathVariable Long idUsuario, @Valid @RequestBody CadastrarIngressoDTO cadastrarIngressoDTO,
                                   UriComponentsBuilder uriBuilder){
        IngressoDTO ingressoDTO = ingresso.cadastrarIngresso(idUsuario, cadastrarIngressoDTO);
        var uri = uriBuilder.path("/ingressos/{id}").buildAndExpand(ingressoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(ingressoDTO);
    }

    @PutMapping("/atualizar")
    @Transactional
    public IngressoDTO atualizar(@RequestBody @Valid AtualizarIngressoDTO atualizarIngressoDTO){
        IngressoDTO ingressoDTO = ingresso.atualizarIngresso(atualizarIngressoDTO);
        return ingressoDTO;
    }
}
