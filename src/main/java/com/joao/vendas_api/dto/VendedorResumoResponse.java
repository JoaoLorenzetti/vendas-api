package com.joao.vendas_api.dto;

import java.math.BigDecimal;

public class VendedorResumoResponse {

    private Long vendedorId;
    private String vendedorNome;
    private Long totalVendas;
    private BigDecimal mediaDiariaVendas;

    public VendedorResumoResponse(Long vendedorId, String vendedorNome,
                                  Long totalVendas, BigDecimal mediaDiariaVendas) {
        this.vendedorId = vendedorId;
        this.vendedorNome = vendedorNome;
        this.totalVendas = totalVendas;
        this.mediaDiariaVendas = mediaDiariaVendas;
    }

    // Getters
    public Long getVendedorId() { return vendedorId; }
    public String getVendedorNome() { return vendedorNome; }
    public Long getTotalVendas() { return totalVendas; }
    public BigDecimal getMediaDiariaVendas() { return mediaDiariaVendas; }
}