package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.StatusVenda;
import com.jpaproject.demo.domain.Venda;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.vendas.VendaResponseDTO;
import com.jpaproject.demo.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService implements IVendaService{


    public final VendaRepository repository;

    public final IClienteService clienteService;

    public final IProdutoService produtoService;

    @Override
    public Venda iniciaVenda(String cpfCliente) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);

        boolean temVendavendente  = repository.findAllByCliente(clienteVenda)
                .stream()
                .anyMatch(venda -> venda.getStatusVenda() == StatusVenda.INICIADA);

            if (temVendavendente){
                throw new IllegalArgumentException("Já há uma venda pendete! Finalize para iniciar uma nova.");
            }

        return repository.save(Venda.criaVenda(clienteVenda));

    }

    @Override
    public Venda addCarrinhoVenda(String cpfCliente, Produto produto) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        Venda venda = repository.buscaVendaIniciada(clienteVenda);
        venda.adicionarAoCarrinho(produto);
        produtoService.cadastrar(produto);
        return repository.save(venda);
    }

    @Override
    public Venda removeCarrinhoVenda(String cpfCliente, Produto produto) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        Venda venda = repository.buscaVendaIniciada(clienteVenda);
        venda.removerDoCarrinho(produto);
        produtoService.cadastrar(produto);
        return repository.save(venda);
    }

    @Override
    public Venda cancelaVenda(String cpfCliente) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        Venda venda = repository.buscaVendaIniciada(clienteVenda);
        venda.cancelarVenda();
        return repository.save(venda);
    }

    @Override
    public Venda finalizaVenda(String cpfCliente) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        Venda venda = repository.buscaVendaIniciada(clienteVenda);
        venda.finalizarVenda();
        return repository.save(venda);
    }

    @Override
    public Venda buscaVendaPendente(String cpfCliente) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        return repository.buscaVendaIniciada(clienteVenda);
    }

    @Override
    public List<Venda> buscaVendasPorCliente(String cpfCliente) {
        return repository.findAllByCliente(clienteService.buscarPorCpf(cpfCliente));
    }

    @Override
    public PageResponse<VendaResponseDTO> buscaVendasPorCliente(String cpfCliente, Pageable pageable) {
        Page<VendaResponseDTO> page = repository.findAllByCliente(clienteService.buscarPorCpf(cpfCliente), pageable).map(VendaResponseDTO::fromEntity);
        return PageResponse.from(page);
    }

    @Override
    public PageResponse<VendaResponseDTO> buscaVendasPendentes(Pageable pageable) {
        Page<VendaResponseDTO> page = repository.buscaVendasPendentes(pageable).map(VendaResponseDTO::fromEntity);
        return PageResponse.from(page);
    }

    @Override
    public PageResponse<VendaResponseDTO> buscaVendasCanceladas(Pageable pageable) {
        Page<VendaResponseDTO> page = repository.buscaVendasCanceladas(pageable).map(VendaResponseDTO::fromEntity);
        return PageResponse.from(page);
    }

    @Override
    public PageResponse<VendaResponseDTO> buscaVendasFinalizadas(Pageable pageable) {
        Page<VendaResponseDTO> page =  repository.buscaVendasFinalizadas(pageable).map(VendaResponseDTO::fromEntity);
        return PageResponse.from(page);
    }
}
