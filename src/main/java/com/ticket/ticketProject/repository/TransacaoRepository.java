package com.ticket.ticketProject.repository;

import com.ticket.ticketProject.model.Ingresso;
import com.ticket.ticketProject.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    Optional<List<Transacao>> findTransacaoByIngresso(Ingresso ingresso);
}
