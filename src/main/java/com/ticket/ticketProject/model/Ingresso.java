package com.ticket.ticketProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingresso")
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String evento;
    private LocalDate data;
    private Double preco;
    private String local;

    @Enumerated(EnumType.STRING)
    private EstadoIngresso estado;

    @OneToMany(mappedBy = "ingresso", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VendedorIngresso> vendedorIngressos;
}
