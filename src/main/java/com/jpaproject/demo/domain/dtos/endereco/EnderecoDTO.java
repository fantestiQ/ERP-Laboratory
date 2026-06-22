package com.jpaproject.demo.domain.dtos.endereco;

import com.jpaproject.demo.domain.Cliente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
       @Size(min = 5, max = 125, message = "O endereço não está dentro do limite de caracteres")
       @NotNull(message = "O endereço não pode ser nulo")
       String endereco,

       @Size(min = 9, max = 11, message = "O cep não está dentro do limite de caracteres")
       @NotNull(message = "O cep não pode ser nulo")
       String cep,

       @Size(min = 5, max = 125, message = "O cidade não está dentro do limite de caracteres")
       @NotNull(message = "O cidade não pode ser nulo")
       String cidade,

       @NotNull(message = "O cidade não pode ser nulo")
       Integer numero,

       @Size(min = 2, max = 2, message = "O uf não está dentro do limite de caracteres")
       @NotNull(message = "O uf não pode ser nulo")
       String uf
) {
}
