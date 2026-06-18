package com.jpaproject.demo.domain.dtos.cliente;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteDTO(
        @Email
        String email,

        String nome,
        String lastName,

        @CPF
        String cpf
) { }
