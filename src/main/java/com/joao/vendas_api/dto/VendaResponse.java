package com.joao.vendas_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VendaResponse {

    private Long id;
    private LocalDate dataVenda;
    private BigDecimal valor;
    private Long vendedorId;
    private String vendedorNome;

    public VendaResponse(Long id, LocalDate dataVenda, BigDecimal valor,
                         Long vendedorId, String vendedorNome) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valor = valor;
        this.vendedorId = vendedorId;
        this.vendedorNome = vendedorNome;
    }

    // Getters
    public Long getId() { return id; }
    public LocalDate getDataVenda() { return dataVenda; }
    public BigDecimal getValor() { return valor; }
    public Long getVendedorId() { return vendedorId; }
    public String getVendedorNome() { return vendedorNome; }
}