package com.jpaproject.demo.domain.dtos.endereco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NomeEnderecoDTO(
        @Size(min = 5, max = 125, message = "O endereço não está dentro dos limites de caracteres!")
        @NotNull(message = "O endereço não pode ser nulo!")
        String endereco
) {
}
