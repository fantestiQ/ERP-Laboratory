package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Produto;

import java.util.List;

public interface IProdutoService {

    Produto cadastrar(Produto produto);

    Produto buscarPorCod(Long cod);

    List<Produto> buscarTodos();

    Produto editar(Long cod, Produto produtoEditado);

    void excluir(Long cod);
}
