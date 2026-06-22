package com.jpaproject.demo.domain.dtos.produto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoDTO(
        @NotNull(message = "O código não pode ser nulo")
        Long codigo,

        @Size(min = 5, max = 255, message = "A descrição não está dentro dos limites de caracteres")
        @NotNull(message = "A descrição não pode ser nula")
        String descricao,

        @Size(min = 5, max = 255, message = "A categoria não está dentro dos limites de caracteres")
        @NotNull(message = "A categoria não pode ser nula")
        String categoria,

        @NotNull(message = "A valor não pode ser nulo")
        BigDecimal valor
) {
}
