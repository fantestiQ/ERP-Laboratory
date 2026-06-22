package com.jpaproject.demo.domain.dtos.endereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record EditaEnderecoDTO(
        @Size(min = 5, max = 125, message = "O endereço não pode ser nulo")
        String enderecoEditar,

        @Valid
        EnderecoDTO dadosEndereco
) {
}
