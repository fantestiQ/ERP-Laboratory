package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Endereco;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoDTO;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEnderecoService {

    Endereco adicionaEnderecoCliente(String cpfCliente, EnderecoDTO endereco);

    Endereco editarEndereco(String nomeEndereco, String cpfCliente, EnderecoDTO enderecoEditado);

    void removerEndereco(String nomeEndereco, String cpfCliente);

    Endereco buscarEndereco(String endereco, String cpfCliente);

    List<Endereco> listaTodosEnderecos(String cpfCliente);

    PageResponse<EnderecoResponseDTO> listaTodosEnderecos(String cpfCliente, Pageable pageable);
}
