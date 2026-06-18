package com.jpaproject.demo.domain.dtos.cliente;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;

import java.math.BigDecimal;
import java.util.List;

public record ClienteResponseDTO (
        Long id, String email, String nome, String lastName, String cpf, List<Endereco> enderecos, BigDecimal saldo
)
{
    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getEmail(), cliente.getPrimeiroNome(), cliente.getLastName(), cliente.getCpf(), cliente.getEnderecos(), cliente.getSaldo());
    }
    public static  ClienteResponseDTO fromEntity(Cliente cliente){
        return  new ClienteResponseDTO(cliente);
    }
}
