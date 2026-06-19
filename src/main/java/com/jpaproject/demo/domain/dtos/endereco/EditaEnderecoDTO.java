package com.jpaproject.demo.domain.dtos.endereco;

public record EditaEnderecoDTO(
        String enderecoEditar,
        EnderecoDTO dadosEndereco
) {
}
