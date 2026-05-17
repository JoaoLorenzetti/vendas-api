package com.joao.vendas_api.service;

import com.joao.vendas_api.dto.CriarVendaRequest;
import com.joao.vendas_api.dto.VendaResponse;
import com.joao.vendas_api.dto.VendedorResumoResponse;
import com.joao.vendas_api.model.Venda;
import com.joao.vendas_api.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @InjectMocks
    private VendaService vendaService;

    private Venda vendaSalva;

    @BeforeEach
    void setUp() {
        vendaSalva = Venda.builder()
                .id(1L)
                .dataVenda(LocalDate.of(2024, 3, 15))
                .valor(new BigDecimal("1500.00"))
                .vendedorId(1L)
                .vendedorNome("Carlos Silva")
                .build();
    }

    @Test
    @DisplayName("Deve criar venda e retornar os dados salvos")
    void deveCriarVenda() {
        CriarVendaRequest request = new CriarVendaRequest();
        request.setDataVenda(LocalDate.of(2024, 3, 15));
        request.setValor(new BigDecimal("1500.00"));
        request.setVendedorId(1L);
        request.setVendedorNome("Carlos Silva");

        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);

        VendaResponse response = vendaService.criarVenda(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getVendedorNome()).isEqualTo("Carlos Silva");
        assertThat(response.getValor()).isEqualByComparingTo("1500.00");
        verify(vendaRepository, times(1)).save(any(Venda.class));
    }

    @Test
    @DisplayName("Deve retornar lista de vendedores no período informado")
    void deveRetornarResumoVendedores() {
        LocalDate dataInicio = LocalDate.of(2024, 1, 1);
        LocalDate dataFim = LocalDate.of(2024, 1, 10);

        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{1L, "Carlos Silva", 5L, 0.5});
        when(vendaRepository.findResumoVendedores(dataInicio, dataFim)).thenReturn(resultado);
        List<VendedorResumoResponse> resumo = vendaService.listarResumoVendedores(dataInicio, dataFim);

        assertThat(resumo).hasSize(1);
        assertThat(resumo.get(0).getVendedorNome()).isEqualTo("Carlos Silva");
        assertThat(resumo.get(0).getTotalVendas()).isEqualTo(5L);
        assertThat(resumo.get(0).getMediaDiariaVendas()).isEqualByComparingTo("0.50");
    }

    @Test
    @DisplayName("Deve lançar exceção quando dataInicio for posterior a dataFim")
    void deveLancarExcecaoParaPeriodoInvalido() {
        LocalDate dataInicio = LocalDate.of(2024, 12, 31);
        LocalDate dataFim = LocalDate.of(2024, 1, 1);

        assertThatThrownBy(() -> vendaService.listarResumoVendedores(dataInicio, dataFim))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("dataInicio");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há vendas no período")
    void deveRetornarListaVaziaQuandoSemVendas() {
        LocalDate dataInicio = LocalDate.of(2024, 6, 1);
        LocalDate dataFim = LocalDate.of(2024, 6, 30);

        when(vendaRepository.findResumoVendedores(dataInicio, dataFim)).thenReturn(List.of());

        List<VendedorResumoResponse> resumo = vendaService.listarResumoVendedores(dataInicio, dataFim);

        assertThat(resumo).isEmpty();
    }
}