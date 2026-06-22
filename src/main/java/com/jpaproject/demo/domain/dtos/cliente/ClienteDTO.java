package com.jpaproject.demo.domain.dtos.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteDTO(
        @Email
        String email,

        @Size(min = 5, max = 125, message = "O nome não está dentro dos limites de caracteres!")
        @NotNull(message = "O nome não pode ser nulo")
        String nome,

        @Size(min = 5, max = 125, message = "O sobrenome não está dentro dos limites de caracteres!")
        @NotNull(message = "O sobrenome não pode ser nulo")
        String lastName,

        @CPF
        String cpf
) { }
