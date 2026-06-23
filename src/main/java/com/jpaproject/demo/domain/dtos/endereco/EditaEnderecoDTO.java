package com.jpaproject.demo.domain.dtos.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record EditaEnderecoDTO(
        @Schema(description = "Endereço que será editado" , example = "Avenida dos Laços")
        @Size(min = 5, max = 125, message = "O endereço não pode ser nulo")
        String enderecoEditar,

        @Schema(description = "Dados do endereço para edição" ,
                example = "endereco: Avenida dos Laços,cep: 09210-100,cidade: São Paulo,numero: 22, uf:SP")
        @Valid
        EnderecoDTO dadosEndereco
) {
}
