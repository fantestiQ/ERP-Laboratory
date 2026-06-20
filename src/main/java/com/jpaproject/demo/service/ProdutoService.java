package com.jpaproject.demo.service;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.produto.ProdutoDTOResponse;
import com.jpaproject.demo.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService implements IProdutoService{


    public final ProdutoRepository repository;

    @Override
    public Produto cadastrar(Produto produto) {
        return repository.save(produto);
    }

    @Override
    public Produto buscarPorCod(Long cod) {
        return repository.findByCodigo(cod);
    }

    @Override
    public List<Produto> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public PageResponse<ProdutoDTOResponse> buscarTodos(Pageable pageable) {
       Page<ProdutoDTOResponse> page = repository.findAll(pageable).map(ProdutoDTOResponse::fromEntity);
       return PageResponse.from(page);
    }

    @Override
    public Produto editar(Long cod, Produto produtoEditado) {
        Produto produto = repository.findByCodigo(cod);
        produto.editaProduto(produtoEditado);
        return repository.save(produto);
    }

    @Override
    public void excluir(Long cod) {
        Produto produto = repository.findByCodigo(cod);
        repository.delete(produto);
    }

    @Override
    public void addQuantidadeMaterial(Integer quantidade, Long cod) {
        Produto produto = buscarPorCod(cod);
        produto.getMaterial().incrementaQuantidade(quantidade);
        repository.save(produto);
    }


}
