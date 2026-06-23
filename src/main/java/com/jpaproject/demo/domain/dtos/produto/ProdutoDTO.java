package com.jpaproject.demo.domain.dtos.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoDTO(
        @Schema(description = "Código de identificação do produto", example = "1000")
        @NotNull(message = "O código não pode ser nulo")
        Long codigo,

        @Schema(description = "Descrição do produto", example = "Monitor Gamer")
        @Size(min = 5, max = 255, message = "A descrição não está dentro dos limites de caracteres")
        @NotNull(message = "A descrição não pode ser nula")
        String descricao,

        @Schema(description = "Categoria do produto", example = "Periférico")
        @Size(min = 5, max = 255, message = "A categoria não está dentro dos limites de caracteres")
        @NotNull(message = "A categoria não pode ser nula")
        String categoria,

        @Schema(description = "Valor do produto", example = "550.00")
        @NotNull(message = "A valor não pode ser nulo")
        BigDecimal valor
) {
}
