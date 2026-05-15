package com.joao.vendas_api.controller;

import com.joao.vendas_api.dto.CriarVendaRequest;
import com.joao.vendas_api.dto.VendaResponse;
import com.joao.vendas_api.dto.VendedorResumoResponse;
import com.joao.vendas_api.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping("/vendas")
    public ResponseEntity<VendaResponse> criarVenda(@Valid @RequestBody CriarVendaRequest request) {
        VendaResponse response = vendaService.criarVenda(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/vendedores/resumo")
    public ResponseEntity<List<VendedorResumoResponse>> resumoVendedores(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        return ResponseEntity.ok(vendaService.listarResumoVendedores(dataInicio, dataFim));
    }
}