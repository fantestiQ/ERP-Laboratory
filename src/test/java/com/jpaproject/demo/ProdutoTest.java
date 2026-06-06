package com.jpaproject.demo;

import com.jpaproject.demo.domain.Material;
import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.service.IProdutoService;
import com.jpaproject.demo.service.ProdutoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class ProdutoTest {
    Produto produto = null;
    Produto produto2 = null;
    List<Produto> initProdutos;


    @Autowired
    IProdutoService produtoService;

    @Test
    public void deveCadastraProduto(){
        Produto produtoCadastrado = produtoService.cadastrar(produto2);
        Assertions.assertEquals(produto2, produtoCadastrado);
    }

    @Test
    public void deveBuscarProduto(){
        Produto produtoCadastrado = produtoService.cadastrar(produto2);
        Produto produtoBuscado = produtoService.buscarPorCod(produto2.getCodigo());
        Assertions.assertEquals(produtoCadastrado, produtoBuscado);
    }

    @Test
    public void deveBuscarTodosProdutos(){
        produtoService.cadastrar(produto2);
        List<Produto> produtos = produtoService.buscarTodos();

        Assertions.assertTrue(produtos.containsAll(initProdutos));
    }

    @Test
    public void deveEditarProduto(){
        Produto produtoBuscado = produtoService.buscarPorCod(produto.getCodigo());
        produtoBuscado.setDescricao("Monitor 40 polegadas");
        produtoBuscado.setValor(new BigDecimal(490));
        Produto produtoEditado= produtoService.editar(produtoBuscado.getCodigo(), produtoBuscado);

       Assertions.assertEquals(produtoBuscado, produtoEditado);
    }

    @Test
    public void deveexcluirProduto(){
        produtoService.excluir(1L);
        Produto produtoExcluido = produtoService.buscarPorCod(1L);

        Assertions.assertNull(produtoExcluido);
    }

    @BeforeEach
    public void init(){
        produto = Produto.criaProduto(1L,"Teclado Gamer", "Periférico", BigDecimal.valueOf(80));
        produto2 = Produto.criaProduto(2L,"Mouse Gamer", "Periférico", BigDecimal.valueOf(60));

        initProdutos = List.of(produto,produto2);
        produtoService.cadastrar(produto);
    }

    @AfterEach
    public void after(){
        List<Produto> produtos = produtoService.buscarTodos();
        produtos.forEach(p -> produtoService.excluir(p.getCodigo()));
    }
}
