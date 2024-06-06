package com.ticket.ticketProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "ingresso")
@Data // Gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String evento;
    private String data;
    private double preco;
    private String local;

    @Enumerated(EnumType.STRING)
    private EstadoIngresso estado;

    @OneToMany(mappedBy = "ingresso", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VendedorIngresso> vendedorIngressos;
}
