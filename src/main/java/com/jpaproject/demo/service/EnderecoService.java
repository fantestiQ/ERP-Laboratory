package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoDTO;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoResponseDTO;
import com.jpaproject.demo.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EnderecoService implements IEnderecoService{


    public final EnderecoRepository repository;


    public final IClienteService clienteService;

    @Override
    public Endereco adicionaEnderecoCliente(String cpfCliente, EnderecoDTO endereco) {
        Cliente cliente = clienteService.buscarPorCpf(cpfCliente);
        Endereco end = new Endereco(endereco, cliente);
        cliente.getEnderecos().add(end);
        clienteService.salvar(cliente);
        return buscarEndereco(end.getEndereco(),cpfCliente);
    }

    @Override
    public Endereco editarEndereco(String nomeEndereco, String cpfCliente, EnderecoDTO enderecoEditado) {
        Endereco endereco = buscarEndereco(nomeEndereco, cpfCliente);
        endereco.editaEndereco(enderecoEditado);
        return  repository.save(endereco);
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
            return repository.findByClienteId(cliente.getId());
        }
        return null;
    }

    @Override
    public PageResponse<EnderecoResponseDTO> listaTodosEnderecos(String cpfCliente, Pageable pageable) {
        Cliente cliente = clienteService.buscarPorCpf(cpfCliente);
        if (cliente != null){
                Page<EnderecoResponseDTO> page = repository.findByClienteId(cliente.getId(), pageable).map(EnderecoResponseDTO::fromEntity);
                return  PageResponse.from(page);
        }
        return null;
    }


}
