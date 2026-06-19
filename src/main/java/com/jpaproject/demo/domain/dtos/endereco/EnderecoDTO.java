package com.jpaproject.demo.domain.dtos.endereco;

import com.jpaproject.demo.domain.Cliente;

public record EnderecoDTO(
       String endereco,
       String cep,
       String cidade,
       Integer numero,
       String uf
) {
}
