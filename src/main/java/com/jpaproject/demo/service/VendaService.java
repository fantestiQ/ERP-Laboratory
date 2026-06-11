package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.StatusVenda;
import com.jpaproject.demo.domain.Venda;
import com.jpaproject.demo.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService implements IVendaService{


    public final VendaRepository repository;

    public final ClienteService clienteService;

    @Override
    public void iniciaVenda(String cpfCliente) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);

        boolean temVendavendente  = repository.findAllByCliente(clienteVenda)
                .stream()
                .anyMatch(venda -> venda.getStatusVenda() == StatusVenda.INICIADA);

            if (temVendavendente){
                throw new IllegalArgumentException("Já há uma venda pendete! Finalize para iniciar uma nova.");
            }

        repository.save(Venda.criaVenda(clienteVenda));

    }

    @Override
    public void addCarrinhoVenda(String cpfCliente, Produto produto) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        Venda venda = repository.buscaVendaIniciada(clienteVenda);
        venda.adicionarAoCarrinho(produto);
        repository.save(venda);

    }

    @Override
    public void cancelaVenda(String cpfCliente) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        Venda venda = repository.buscaVendaIniciada(clienteVenda);
        venda.cancelarVenda();
        repository.save(venda);
    }

    @Override
    public void finalizaVenda(String cpfCliente) {
        Cliente clienteVenda = clienteService.buscarPorCpf(cpfCliente);
        Venda venda = repository.buscaVendaIniciada(clienteVenda);
        venda.finalizarVenda();
        repository.save(venda);
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
    public List<Venda> buscaVendasPendentes() {
        return repository.buscaVendasPendentes();
    }

    @Override
    public List<Venda> buscaVendasCanceladas() {
        return repository.buscaVendasCanceladas();
    }

    @Override
    public List<Venda> buscaVendasFinalizadas() {
        return repository.buscaVendasFinalizadas();
    }
}
