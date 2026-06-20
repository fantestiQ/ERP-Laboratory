package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.produto.ProdutoDTOResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProdutoService {

    Produto cadastrar(Produto produto);

    Produto buscarPorCod(Long cod);

    List<Produto> buscarTodos();

    PageResponse<ProdutoDTOResponse> buscarTodos(Pageable pageable);

    Produto editar(Long cod, Produto produtoEditado);

    void excluir(Long cod);

    void addQuantidadeMaterial(Integer quantidade, Long cod);
}
