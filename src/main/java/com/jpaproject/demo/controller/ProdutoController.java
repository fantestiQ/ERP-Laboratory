package com.jpaproject.demo.controller;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.produto.ProdutoDTO;
import com.jpaproject.demo.domain.dtos.produto.ProdutoDTOResponse;
import com.jpaproject.demo.service.IProdutoService;
import jakarta.validation.Valid;
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
@RequestMapping("api/v1/produtos")
public class ProdutoController {

    public final IProdutoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDTOResponse> cadastraProduto(@RequestBody @Valid ProdutoDTO dados){
       Produto produto = Produto.criaProduto(dados.codigo(), dados.descricao(), dados.categoria(), dados.valor());
       ProdutoDTOResponse response = new ProdutoDTOResponse(service.cadastrar(produto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{cod}")
    public  ResponseEntity<ProdutoDTOResponse> buscaProduto(@PathVariable Long cod){
        return ResponseEntity.ok(new ProdutoDTOResponse(service.buscarPorCod(cod)));
    }

    @GetMapping
    public  ResponseEntity<PageResponse<ProdutoDTOResponse>> buscaTodosProduto(@PageableDefault(
                                                                                            size = 20,
                                                                                            sort = "descricao",
                                                                                            direction =  Sort.Direction.ASC)
                                                                                   Pageable pageable){
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }

    @PutMapping("/{cod}")
    public ResponseEntity<ProdutoDTOResponse> editaProduto(@PathVariable Long cod, @RequestBody @Valid ProdutoDTO dados){
        Produto produtoEditado = Produto.criaProduto(dados.codigo(), dados.descricao(), dados.categoria(), dados.valor());
       return ResponseEntity.ok( new ProdutoDTOResponse(service.editar(cod,produtoEditado)));
    }

    @PutMapping("add/{cod}/{qnt}")
    public ResponseEntity addQuantidadeProduto(@PathVariable Long cod, @PathVariable Integer qnt){
        service.addQuantidadeMaterial(qnt,cod);
        return ResponseEntity.noContent().build();
    }

}
