package com.joao.vendas_api.service;

import com.joao.vendas_api.dto.CriarVendaRequest;
import com.joao.vendas_api.dto.VendaResponse;
import com.joao.vendas_api.dto.VendedorResumoResponse;
import com.joao.vendas_api.model.Venda;
import com.joao.vendas_api.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public VendaResponse criarVenda(CriarVendaRequest request) {
        Venda venda = Venda.builder()
                .dataVenda(request.getDataVenda())
                .valor(request.getValor())
                .vendedorId(request.getVendedorId())
                .vendedorNome(request.getVendedorNome())
                .build();

        Venda salva = vendaRepository.save(venda);

        return new VendaResponse(
                salva.getId(),
                salva.getDataVenda(),
                salva.getValor(),
                salva.getVendedorId(),
                salva.getVendedorNome()
        );
    }

    public List<VendedorResumoResponse> listarResumoVendedores(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("dataInicio não pode ser posterior a dataFim");
        }

        List<Object[]> resultados = vendaRepository.findResumoVendedores(dataInicio, dataFim);

        return resultados.stream()
                .map(row -> new VendedorResumoResponse(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        BigDecimal.valueOf(((Number) row[3]).doubleValue())
                                .setScale(2, RoundingMode.HALF_UP)
                ))
                .toList();
    }
}