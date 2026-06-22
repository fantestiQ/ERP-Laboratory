package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.Venda;

import java.util.List;

public interface IVendaService {

    Venda iniciaVenda(String cpfCliente);

    Venda addCarrinhoVenda(String cpfCliente, Produto produto);

    Venda cancelaVenda(String cpfCliente);

    Venda finalizaVenda(String cpfCliente);

    Venda buscaVendaPendente(String cpfCliente);

    List<Venda> buscaVendasPorCliente(String cpfCliente);

    List<Venda> buscaVendasPendentes();

    List<Venda> buscaVendasCanceladas();

    List<Venda> buscaVendasFinalizadas();
}
