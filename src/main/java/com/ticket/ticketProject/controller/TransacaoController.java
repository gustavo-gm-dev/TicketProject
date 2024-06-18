package com.ticket.ticketProject.controller;

import com.ticket.ticketProject.model.dto.transacao.CadastrarTransacaoDTO;
import com.ticket.ticketProject.model.dto.transacao.TransacaoDTO;
import com.ticket.ticketProject.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacao;

    @GetMapping("{idIngresso}")
    public List<TransacaoDTO> getAllTransacoes(@PathVariable Long idIngresso) {
        return transacao.getAllTransacoes(idIngresso);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity createTransacao(@RequestBody @Valid CadastrarTransacaoDTO cadastrarTransacaoDTO,
                                          UriComponentsBuilder uriBuilder){
        TransacaoDTO transacaoDTO = transacao.createTransacao(cadastrarTransacaoDTO);
        var uri = uriBuilder.path("/cadastrar/{id}").buildAndExpand(transacaoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(transacaoDTO);
    }

    @PutMapping("/pagamento/{idIngresso}")
    public TransacaoDTO confirmarPagamento(@PathVariable Long idIngresso){
        TransacaoDTO transacaoDTO = transacao.confirmarPagamento(idIngresso);
        return transacaoDTO;
    }

    @PutMapping("/recebimento/{idIngresso}")
    public TransacaoDTO confirmarRecebimento(@PathVariable Long idIngresso){
        TransacaoDTO transacaoDTO = transacao.confirmarRecebimento(idIngresso);
        return transacaoDTO;
    }

}
