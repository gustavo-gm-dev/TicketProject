package com.ticket.ticketProject.service;

import com.ticket.ticketProject.model.Transacao;

import java.util.List;

public interface TransacaoService {

    List<Transacao> getAllTransacoes();

    Transacao createTransacao (Transacao transacao);

    Transacao confirmarPagamento(Long id);

    Transacao confirmarRecebimento(Long id);

    Transacao estornarPagamento(Long id);
}
