package com.ticket.ticketProject.service;

import com.ticket.ticketProject.model.dto.transacao.CadastrarTransacaoDTO;
import com.ticket.ticketProject.model.dto.transacao.TransacaoDTO;

import java.util.List;

public interface TransacaoService {

    List<TransacaoDTO> getAllTransacoes(Long idIngresso);

    TransacaoDTO createTransacao (CadastrarTransacaoDTO cadastrartransacaoDTO);

    TransacaoDTO confirmarPagamento(Long idIngresso);

    TransacaoDTO confirmarRecebimento(Long idIngresso);
}
