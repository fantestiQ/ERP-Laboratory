package com.jpaproject.demo.controller;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.dtos.vendas.VendaResponseDTO;
import com.jpaproject.demo.service.IProdutoService;
import com.jpaproject.demo.service.IVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vendas")
public class VendaController {

    public final IVendaService service;
    public final IProdutoService produtoService;


    @PostMapping("/{cpf}")
    @Transactional
    public ResponseEntity<VendaResponseDTO> criaVenda(@PathVariable String cpf){
        VendaResponseDTO response = new VendaResponseDTO(service.iniciaVenda(cpf));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
       return ResponseEntity.created(uri).body(response);
    }
    @PutMapping(("add/{cpf}/{cod}"))
    public ResponseEntity<VendaResponseDTO> addProdutoVenda(@PathVariable String cpf, @PathVariable Long cod){
        return ResponseEntity.ok(new VendaResponseDTO(service.addCarrinhoVenda(cpf,produtoService.buscarPorCod(cod))));
    }
    @PutMapping(("cancel/{cpf}"))
    public ResponseEntity<VendaResponseDTO> cancelaVenda(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.cancelaVenda(cpf)));
    }
    @PutMapping(("close/{cpf}"))
    public ResponseEntity<VendaResponseDTO> finalizaVenda(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.finalizaVenda(cpf)));
    }
    @GetMapping(("{cpf}"))
    public ResponseEntity<VendaResponseDTO> buscaVendaCliente(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.buscaVendaPendente(cpf)));
    }
}
