package com.jpaproject.demo.domain.dtos.produto;

import java.math.BigDecimal;

public record ProdutoDTO(
        Long codigo,
        String descricao,
        String categoria,
        BigDecimal valor
) {
}
