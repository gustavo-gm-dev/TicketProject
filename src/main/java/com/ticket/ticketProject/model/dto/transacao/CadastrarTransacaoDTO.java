package com.ticket.ticketProject.model.dto.transacao;

import com.ticket.ticketProject.model.EstadoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastrarTransacaoDTO(
        @NotNull
        LocalDate date,
        @NotNull
        Long idCliente,
        @NotNull
        Long idIngresso,
        @NotNull
        Double valor
) {
}
