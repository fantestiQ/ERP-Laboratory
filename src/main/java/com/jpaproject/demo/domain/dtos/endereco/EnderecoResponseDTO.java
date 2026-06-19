package com.jpaproject.demo.domain.dtos.endereco;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;

public record EnderecoResponseDTO(
        Long id,
        String endereco,
        String cep,
        String cidade,
        Integer numero,
        String uf
) {
    public EnderecoResponseDTO (Endereco endereco){
        this(endereco.getId(), endereco.getEndereco(), endereco.getCep(), endereco.getCidade(), endereco.getNumero(), endereco.getUf());
    }

    public static EnderecoResponseDTO fromEntity(Endereco endereco){
       return new EnderecoResponseDTO(endereco);
    }
}
