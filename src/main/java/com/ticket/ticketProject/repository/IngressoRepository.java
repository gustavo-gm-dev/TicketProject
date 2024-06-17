package com.ticket.ticketProject.repository;

import com.ticket.ticketProject.model.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
}
