package com.ticket.ticketProject.model.dto.ingresso;

import com.ticket.ticketProject.model.EstadoIngresso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastrarIngressoDTO(
        @NotBlank
        String evento,
        @NotNull
        LocalDate data,
        @NotNull
        Double preco,
        @NotBlank
        String local,
        @NotNull
        EstadoIngresso estado
){
}
