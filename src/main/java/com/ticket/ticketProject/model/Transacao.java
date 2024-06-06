package com.ticket.ticketProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transacao")
@Data // Gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
