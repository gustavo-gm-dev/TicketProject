package com.ticket.ticketProject.model.dto.ingresso;

import com.ticket.ticketProject.model.EstadoIngresso;

import java.time.LocalDate;

public record IngressoDTO(
        Integer id,
        String evento,
        LocalDate data,
        Double preco,
        String local,
        EstadoIngresso estado) {
}
