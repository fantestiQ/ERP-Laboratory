package com.jpaproject.demo.domain.dtos.produto;

import com.jpaproject.demo.domain.Material;
import com.jpaproject.demo.domain.Produto;

import java.math.BigDecimal;

public record ProdutoDTOResponse (
        Long id,
        Long codigo,
        String descricao,
        String categoria,
        BigDecimal valor,
        Material material
){
    public ProdutoDTOResponse (Produto produto){
        this(produto.getId(), produto.getCodigo(), produto.getDescricao(), produto.getCategoria(), produto.getValor(),produto.getMaterial());
    }

    public static ProdutoDTOResponse fromEntity(Produto produto){
        return new ProdutoDTOResponse(produto);
    }
}
