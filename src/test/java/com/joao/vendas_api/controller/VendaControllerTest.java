package com.joao.vendas_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joao.vendas_api.dto.CriarVendaRequest;
import com.joao.vendas_api.dto.VendaResponse;
import com.joao.vendas_api.dto.VendedorResumoResponse;
import com.joao.vendas_api.service.VendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendaController.class)
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VendaService vendaService;

    @Test
    @DisplayName("POST /vendas - deve retornar 201 com a venda criada")
    void deveCriarVenda() throws Exception {
        CriarVendaRequest request = new CriarVendaRequest();
        request.setDataVenda(LocalDate.of(2024, 3, 15));
        request.setValor(new BigDecimal("1500.00"));
        request.setVendedorId(1L);
        request.setVendedorNome("Carlos Silva");

        VendaResponse response = new VendaResponse(
                1L,
                LocalDate.of(2024, 3, 15),
                new BigDecimal("1500.00"),
                1L,
                "Carlos Silva"
        );

        when(vendaService.criarVenda(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.vendedorNome").value("Carlos Silva"));
    }

    @Test
    @DisplayName("POST /vendas - deve retornar 400 quando body inválido")
    void deveRetornar400QuandoBodyInvalido() throws Exception {
        String bodyInvalido = """
                {
                    "dataVenda": null,
                    "valor": -100,
                    "vendedorId": null,
                    "vendedorNome": null
                }
                """;

        mockMvc.perform(post("/api/v1/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /vendedores/resumo - deve retornar 200 com lista de vendedores")
    void deveRetornarResumoVendedores() throws Exception {
        List<VendedorResumoResponse> resumo = List.of(
                new VendedorResumoResponse(1L, "Carlos Silva", 10L, new BigDecimal("1.00"))
        );

        when(vendaService.listarResumoVendedores(
                eq(LocalDate.of(2024, 1, 1)),
                eq(LocalDate.of(2024, 1, 31))
        )).thenReturn(resumo);

        mockMvc.perform(get("/api/v1/vendedores/resumo")
                        .param("dataInicio", "2024-01-01")
                        .param("dataFim", "2024-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].vendedorNome").value("Carlos Silva"))
                .andExpect(jsonPath("$[0].totalVendas").value(10));
    }

    @Test
    @DisplayName("GET /vendedores/resumo - deve retornar 400 para período inválido")
    void deveRetornar400ParaPeriodoInvalido() throws Exception {
        when(vendaService.listarResumoVendedores(any(), any()))
                .thenThrow(new IllegalArgumentException("dataInicio não pode ser posterior a dataFim"));

        mockMvc.perform(get("/api/v1/vendedores/resumo")
                        .param("dataInicio", "2024-12-31")
                        .param("dataFim", "2024-01-01"))
                .andExpect(status().isBadRequest());
    }
}