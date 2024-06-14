package com.ticket.ticketProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "cliente_ingresso_id")
    private ClienteIngresso clienteIngresso;

    @ManyToOne
    @JoinColumn(name = "vendedor_ingresso_id")
    private VendedorIngresso vendedorIngresso;

    @ManyToOne
    @JoinColumn(name = "ingresso_id")
    private Ingresso ingresso;

    private double valor;
    @Enumerated(EnumType.STRING)
    private EstadoTransacao estado;
}
