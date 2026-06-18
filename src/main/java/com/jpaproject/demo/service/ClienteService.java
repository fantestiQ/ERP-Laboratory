package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.cliente.ClienteResponseDTO;
import com.jpaproject.demo.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService{


    public final ClienteRepository repository;

    @Override
    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
       Optional<Cliente> cliente = repository.findByCpf(cpf);

        return cliente.orElse(null);
    }

    @Override
    public void remover(String cpf) {
        Cliente cliente = buscarPorCpf(cpf);
        repository.delete(cliente);
    }

    @Override
    public Cliente editar(String cpf, Cliente clienteEditado) {
        Optional<Cliente> cliente = repository.findByCpf(cpf);
        Cliente clienteBuscado = cliente.orElse(null);
        if (clienteBuscado != null){
            clienteBuscado.editaCliente(clienteEditado);
            return salvar(clienteBuscado);
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos() {
        return List.of();
    }

    @Override
    public PageResponse<ClienteResponseDTO> listarTodos(Pageable pageable) {
        Page<ClienteResponseDTO> page = repository.findAll(pageable).map(ClienteResponseDTO::fromEntity);
        return  PageResponse.from(page);
    }

}
