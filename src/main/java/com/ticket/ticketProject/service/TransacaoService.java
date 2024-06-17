package com.ticket.ticketProject.service;

import com.ticket.ticketProject.model.dto.transacao.CadastrarTransacaoDTO;
import com.ticket.ticketProject.model.dto.transacao.TransacaoDTO;

import java.util.List;

public interface TransacaoService {

    List<TransacaoDTO> getAllTransacoes(Long id);

    TransacaoDTO createTransacao (CadastrarTransacaoDTO cadastrartransacaoDTO);

    TransacaoDTO confirmarPagamento(Long id);

    TransacaoDTO confirmarRecebimento(Long id);

    TransacaoDTO estornarPagamento(Long id);
}
