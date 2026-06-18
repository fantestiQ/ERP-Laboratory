package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.cliente.ClienteResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    Cliente salvar(Cliente cliente);

    Cliente buscarPorCpf(String cpf);

    void remover(String cpf);

    Cliente editar(String cpf, Cliente clienteEditado);

    List<Cliente> listarTodos();

    PageResponse<ClienteResponseDTO> listarTodos(Pageable pageable);
}
