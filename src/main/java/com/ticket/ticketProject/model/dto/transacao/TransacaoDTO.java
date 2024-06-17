package com.ticket.ticketProject.model.dto.transacao;

import com.ticket.ticketProject.model.EstadoTransacao;

import java.time.LocalDate;

public record TransacaoDTO(
        Long id,
        LocalDate date,
        Long idCliente,
        Long idVendedor,
        Long idIngresso,
        Double valor,
        EstadoTransacao estado
) {
}
