package com.ticket.ticketProject.model.dto.usuario;

import java.time.LocalDate;

public record UsuarioDTO(Integer id, String cpf, String nome, LocalDate nascimento, String email) {
}
