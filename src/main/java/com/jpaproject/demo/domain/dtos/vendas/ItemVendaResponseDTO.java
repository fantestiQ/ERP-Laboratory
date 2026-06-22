package com.jpaproject.demo.domain.dtos.vendas;

import com.jpaproject.demo.domain.Produto;

import java.math.BigDecimal;

public record ItemVendaResponseDTO(
        Long cod,
        String descricao,
        BigDecimal valor
) {
    public ItemVendaResponseDTO(Produto produto) {
        this(produto.getCodigo(), produto.getDescricao(), produto.getValor());
    }
}
