package com.ticket.ticketProject.repository;

import com.ticket.ticketProject.model.Ingresso;
import com.ticket.ticketProject.model.Usuario;
import com.ticket.ticketProject.model.VendedorIngresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorIngressoRepository extends JpaRepository<VendedorIngresso, Long> {
    Optional<VendedorIngresso> findVendedorIngressoByVendedorAndIngresso(Usuario vendedor, Ingresso ingresso);
}
