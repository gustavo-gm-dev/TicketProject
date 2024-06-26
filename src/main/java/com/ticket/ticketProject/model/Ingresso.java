package com.ticket.ticketProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private EstadoIngresso estado;

    @OneToOne(mappedBy = "ingresso", cascade = CascadeType.ALL, orphanRemoval = true)
    private VendedorIngresso vendedorIngressos;
}
