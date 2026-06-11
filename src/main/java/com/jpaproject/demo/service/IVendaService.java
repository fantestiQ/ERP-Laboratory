package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.Venda;

import java.util.List;

public interface IVendaService {

    void iniciaVenda(String cpfCliente);

    void addCarrinhoVenda(String cpfCliente, Produto produto);

    void cancelaVenda(String cpfCliente);

    void finalizaVenda(String cpfCliente);

    Venda buscaVendaPendente(String cpfCliente);

    List<Venda> buscaVendasPorCliente(String cpfCliente);

    List<Venda> buscaVendasPendentes();

    List<Venda> buscaVendasCanceladas();

    List<Venda> buscaVendasFinalizadas();
}
