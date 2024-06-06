package com.ticket.ticketProject.repository;

import com.ticket.ticketProject.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}