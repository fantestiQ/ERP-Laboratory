package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;
import com.jpaproject.demo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EnderecoService implements IEnderecoService{

    @Autowired
    EnderecoRepository repository;

    @Autowired
    IClienteService clienteService;

    @Override
    public Endereco editarEndereco(String nomeEndereco, String cpfCliente, Endereco enderecoEditado) {
        return null;
    }

    @Override
    public void removerEndereco(String nomeEndereco, String cpfCliente) {
        Cliente cliente = clienteService.buscarPorCpf(cpfCliente);
        if (cliente != null){
            List<Endereco> enderecos = repository.findByClienteId(cliente.getId());
            Optional<Endereco> enderecoOp =  enderecos.stream().filter(e -> e.getEndereco().equals(nomeEndereco)).findFirst();
            Endereco endereco = enderecoOp.orElse(null);

            if (endereco != null){
                enderecos.remove(endereco);
                cliente.setEnderecos(enderecos);
                repository.delete(endereco);
                clienteService.salvar(cliente);
            }
        }
    }

    @Override
    public Endereco buscarEndereco(String nomeEndereco, String cpfCliente) {
        Cliente cliente = clienteService.buscarPorCpf(cpfCliente);
        if (cliente != null){
            List<Endereco> enderecos = repository.findByClienteId(cliente.getId());

          Optional<Endereco> enderecoOp =  enderecos.stream().filter(e -> e.getEndereco().equals(nomeEndereco)).findFirst();
          return enderecoOp.orElse(null);
        }

        return null;
    }

    @Override
    public List<Endereco> listaTodosEnderecos(String cpfCliente) {
        Cliente cliente = clienteService.buscarPorCpf(cpfCliente);
        if (cliente != null){
           return  repository.findByClienteId(cliente.getId());
        }
        return null;
    }
}
