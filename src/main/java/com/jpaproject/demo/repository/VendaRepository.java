package com.jpaproject.demo.repository;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VendaRepository extends JpaRepository<Venda,Long> {

    List<Venda> findAllByCliente(Cliente cliente);

    @Query("SELECT v FROM Venda v WHERE v.statusVenda = StatusVenda.INICIADA")
    Venda buscaVendaIniciada(Cliente cliente);

    @Query("SELECT v FROM Venda v where v.statusVenda = StatusVenda.INICIADA")
    List<Venda> buscaVendasPendentes();

    @Query("SELECT v FROM Venda v where v.statusVenda = StatusVenda.CANCELADA")
    List<Venda> buscaVendasCanceladas();

    @Query("SELECT v FROM Venda v where v.statusVenda = StatusVenda.FINALIZADA")
    List<Venda> buscaVendasFinalizadas();

}
