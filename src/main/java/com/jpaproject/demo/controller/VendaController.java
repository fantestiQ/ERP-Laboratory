package com.jpaproject.demo.controller;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.vendas.VendaResponseDTO;
import com.jpaproject.demo.service.IProdutoService;
import com.jpaproject.demo.service.IVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @PutMapping(("remove/{cpf}/{cod}"))
    public ResponseEntity<VendaResponseDTO> removeProdutoVenda(@PathVariable String cpf, @PathVariable Long cod){
        return ResponseEntity.ok(new VendaResponseDTO(service.removeCarrinhoVenda(cpf,produtoService.buscarPorCod(cod))));
    }
    @PutMapping(("close/{cpf}"))
    public ResponseEntity<VendaResponseDTO> finalizaVenda(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.finalizaVenda(cpf)));
    }
    @GetMapping(("{cpf}"))
    public ResponseEntity<VendaResponseDTO> buscaVendaCliente(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.buscaVendaPendente(cpf)));
    }
    @GetMapping(("all/{cpf}"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasCliente(@PathVariable String cpf,
                                                           @PageableDefault(size = 20,
                                                               sort = "dataVenda",
                                                               direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasPorCliente(cpf,pageable));
    }
    @GetMapping(("pendentes"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasPendentes(@PageableDefault(size = 20,
                                                                                     sort = "dataVenda",
                                                                                     direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasPendentes(pageable));
    }
    @GetMapping(("canceladas"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasCanceladas(@PageableDefault(size = 20,
                                                                                       sort = "dataVenda",
                                                                                       direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasCanceladas(pageable));
    }
    @GetMapping(("finalizadas"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasFinalizadas(@PageableDefault(size = 20,
                                                                                        sort = "dataVenda",
                                                                                        direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasFinalizadas(pageable));
    }

}
