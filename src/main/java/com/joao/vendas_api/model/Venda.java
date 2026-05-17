package com.joao.vendas_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vendas")
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

    public Venda() {}

    public Venda(Long id, LocalDate dataVenda, BigDecimal valor, Long vendedorId, String vendedorNome) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valor = valor;
        this.vendedorId = vendedorId;
        this.vendedorNome = vendedorNome;
    }

    public static VendaBuilder builder() {
        return new VendaBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Long getVendedorId() { return vendedorId; }
    public void setVendedorId(Long vendedorId) { this.vendedorId = vendedorId; }

    public String getVendedorNome() { return vendedorNome; }
    public void setVendedorNome(String vendedorNome) { this.vendedorNome = vendedorNome; }

    public static class VendaBuilder {
        private Long id;
        private LocalDate dataVenda;
        private BigDecimal valor;
        private Long vendedorId;
        private String vendedorNome;

        public VendaBuilder id(Long id) { this.id = id; return this; }
        public VendaBuilder dataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; return this; }
        public VendaBuilder valor(BigDecimal valor) { this.valor = valor; return this; }
        public VendaBuilder vendedorId(Long vendedorId) { this.vendedorId = vendedorId; return this; }
        public VendaBuilder vendedorNome(String vendedorNome) { this.vendedorNome = vendedorNome; return this; }

        public Venda build() {
            return new Venda(id, dataVenda, valor, vendedorId, vendedorNome);
        }
    }
}