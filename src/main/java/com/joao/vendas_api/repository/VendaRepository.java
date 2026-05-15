package com.joao.vendas_api.repository;

import com.joao.vendas_api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("""
        SELECT v.vendedorId,
               v.vendedorNome,
               COUNT(v.id),
               COUNT(v.id) * 1.0 / (CAST(DATEDIFF(DAY, :dataInicio, :dataFim) + 1 AS double))
        FROM Venda v
        WHERE v.dataVenda BETWEEN :dataInicio AND :dataFim
        GROUP BY v.vendedorId, v.vendedorNome
        ORDER BY COUNT(v.id) DESC
        """)
    List<Object[]> findResumoVendedores(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );
}