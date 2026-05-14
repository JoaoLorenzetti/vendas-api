package com.joao.vendas_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vendas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_venda", nullable = false)
    private LocalDate dataVenda;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(name = "vendedor_id", nullable = false)
    private Long vendedorId;

    @NotNull
    @Column(name = "vendedor_nome", nullable = false)
    private String vendedorNome;
}