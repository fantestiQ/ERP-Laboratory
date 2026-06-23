package com.jpaproject.demo.domain.dtos.endereco;

import com.jpaproject.demo.domain.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
       @Schema(description = "Endereço" , example = "Avenida dos Laços")
       @Size(min = 5, max = 125, message = "O endereço não está dentro do limite de caracteres")
       @NotNull(message = "O endereço não pode ser nulo")
       String endereco,

       @Schema(description = "CEP do cliente" , example = "09222-100")
       @Size(min = 9, max = 11, message = "O cep não está dentro do limite de caracteres")
       @NotNull(message = "O cep não pode ser nulo")
       String cep,

       @Schema(description = "Cidade do cliente" , example = "São Paulo")
       @Size(min = 5, max = 125, message = "O cidade não está dentro do limite de caracteres")
       @NotNull(message = "O cidade não pode ser nulo")
       String cidade,

       @Schema(description = "Número do endereço do cliente" , example = "211")
       @NotNull(message = "O número não pode ser nulo")
       Integer numero,

       @Schema(description = "UF do endereço do cliente" , example = "SP")
       @Size(min = 2, max = 2, message = "O uf não está dentro do limite de caracteres")
       @NotNull(message = "O uf não pode ser nulo")
       String uf
) {
}
