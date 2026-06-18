package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Endereco;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoDTO;

import java.util.List;

public interface IEnderecoService {

    Endereco adicionaEnderecoCliente(String cpfCliente, EnderecoDTO endereco);

    Endereco editarEndereco(String nomeEndereco, String cpfCliente, Endereco enderecoEditado);

    void removerEndereco(String nomeEndereco, String cpfCliente);

    Endereco buscarEndereco(String endereco, String cpfCliente);

    List<Endereco> listaTodosEnderecos(String cpfCliente);
}
