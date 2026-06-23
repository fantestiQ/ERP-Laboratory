package com.jpaproject.demo.controller;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.vendas.VendaResponseDTO;
import com.jpaproject.demo.service.IProdutoService;
import com.jpaproject.demo.service.IVendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Vendas", description = "Operações relacionadas a vendas")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    public final IVendaService service;
    public final IProdutoService produtoService;


    @Operation(summary = "Cria venda")
    @ApiResponse(responseCode = "201", description = "Venda iniciada com sucesso")
    @PostMapping("/{cpf}")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VendaResponseDTO> criaVenda(@PathVariable String cpf){
        VendaResponseDTO response = new VendaResponseDTO(service.iniciaVenda(cpf));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
       return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Adiciona produto na venda por código")
    @ApiResponse(responseCode = "200", description = "Produto adicionado na venda com sucesso")
    @PutMapping(("add/{cpf}/{cod}"))
    public ResponseEntity<VendaResponseDTO> addProdutoVenda(@PathVariable String cpf, @PathVariable Long cod){
        return ResponseEntity.ok(new VendaResponseDTO(service.addCarrinhoVenda(cpf,produtoService.buscarPorCod(cod))));
    }

    @Operation(summary = "Cancela venda por código")
    @ApiResponse(responseCode = "200", description = "Venda cancelada com sucesso")
    @PutMapping(("cancel/{cpf}"))
    public ResponseEntity<VendaResponseDTO> cancelaVenda(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.cancelaVenda(cpf)));
    }

    @Operation(summary = "Remove produto da venda por código")
    @ApiResponse(responseCode = "200", description = "Produto removido da venda cancelada com sucesso")
    @PutMapping(("remove/{cpf}/{cod}"))
    public ResponseEntity<VendaResponseDTO> removeProdutoVenda(@PathVariable String cpf, @PathVariable Long cod){
        return ResponseEntity.ok(new VendaResponseDTO(service.removeCarrinhoVenda(cpf,produtoService.buscarPorCod(cod))));
    }

    @Operation(summary = "Finaiza venda por CPF")
    @ApiResponse(responseCode = "200", description = "Venda finalizada com sucesso")
    @PutMapping(("close/{cpf}"))
    public ResponseEntity<VendaResponseDTO> finalizaVenda(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.finalizaVenda(cpf)));
    }

    @Operation(summary = "Busca venda pendente do cliente por CPF")
    @ApiResponse(responseCode = "200", description = "Venda listada com sucesso")
    @GetMapping(("{cpf}"))
    public ResponseEntity<VendaResponseDTO> buscaVendaCliente(@PathVariable String cpf){
        return ResponseEntity.ok(new VendaResponseDTO(service.buscaVendaPendente(cpf)));
    }

    @Operation(summary = "Lista vendas do cliente por CPF")
    @ApiResponse(responseCode = "200", description = "Vendas listadas com sucesso")
    @GetMapping(("all/{cpf}"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasCliente(@PathVariable String cpf,
                                                           @PageableDefault(size = 20,
                                                               sort = "dataVenda",
                                                               direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasPorCliente(cpf,pageable));
    }

    @Operation(summary = "Lista vendas pendentes")
    @ApiResponse(responseCode = "200", description = "Vendas pendetes listadas com sucesso")
    @GetMapping(("pendentes"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasPendentes(@PageableDefault(size = 20,
                                                                                     sort = "dataVenda",
                                                                                     direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasPendentes(pageable));
    }

    @Operation(summary = "Lista vendas canceladas")
    @ApiResponse(responseCode = "200", description = "Vendas canceladas listadas com sucesso")
    @GetMapping(("canceladas"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasCanceladas(@PageableDefault(size = 20,
                                                                                       sort = "dataVenda",
                                                                                       direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasCanceladas(pageable));
    }

    @Operation(summary = "Lista vendas finalizadas")
    @ApiResponse(responseCode = "200", description = "Vendas finalizadas listadas com sucesso")
    @GetMapping(("finalizadas"))
    public ResponseEntity<PageResponse<VendaResponseDTO>> buscaVendasFinalizadas(@PageableDefault(size = 20,
                                                                                        sort = "dataVenda",
                                                                                        direction =  Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.buscaVendasFinalizadas(pageable));
    }

}
