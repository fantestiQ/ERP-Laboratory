package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;

import java.util.List;

public interface IClienteService {

    Cliente salvar(Cliente cliente);

    Cliente buscarPorCpf(String cpf);

    void remover(String cpf);

    Cliente editar(String cpf, Cliente clienteEditado);

    List<Cliente> listarTodos();
}
