package com.jpaproject.demo.domain.dtos.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Dados para cadastro de cliente")
public record ClienteDTO(
        @Schema(description = "Email do cliente" , example = "teste@email.com")
        @Email
        String email,

        @Schema(description = "Primeiro nome do cliente" , example = "Isac")
        @Size(min = 5, max = 125, message = "O nome não está dentro dos limites de caracteres!")
        @NotNull(message = "O nome não pode ser nulo")
        String nome,

        @Schema(description = "Sobrenome do cliente" , example = "dos Santos")
        @Size(min = 5, max = 125, message = "O sobrenome não está dentro dos limites de caracteres!")
        @NotNull(message = "O sobrenome não pode ser nulo")
        String lastName,

        @Schema(description = "CPF do cliente" , example = "07690191029")
        @CPF
        String cpf
) { }
