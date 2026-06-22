package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.Venda;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.vendas.VendaResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVendaService {

    Venda iniciaVenda(String cpfCliente);

    Venda addCarrinhoVenda(String cpfCliente, Produto produto);

    Venda removeCarrinhoVenda(String cpfCliente, Produto produto);

    Venda cancelaVenda(String cpfCliente);

    Venda finalizaVenda(String cpfCliente);

    Venda buscaVendaPendente(String cpfCliente);

    List<Venda> buscaVendasPorCliente(String cpfCliente);

    PageResponse<VendaResponseDTO> buscaVendasPorCliente(String cpfCliente, Pageable pageable);

    PageResponse<VendaResponseDTO> buscaVendasPendentes(Pageable pageable);

    PageResponse<VendaResponseDTO> buscaVendasCanceladas(Pageable pageable);

    PageResponse<VendaResponseDTO> buscaVendasFinalizadas(Pageable pageable);
}
