package com.jpaproject.demo;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.StatusVenda;
import com.jpaproject.demo.domain.Venda;
import com.jpaproject.demo.repository.VendaRepository;
import com.jpaproject.demo.service.ClienteService;
import com.jpaproject.demo.service.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaTest {

    @Mock
    private VendaRepository repository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private VendaService vendaService;

    private Cliente clienteMock;
    private static final String CPF = "1412312311";

    @BeforeEach
    void setUp() {
        clienteMock = new Cliente(
                ">.<@teste.com",
                "Gyro",
                "Zeppeli",
                CPF
        );
    }

    // ----------------------------------------------------------------
    // iniciaVenda
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Deve iniciar uma venda quando cliente não tem venda pendente")
    void deveIniciarVenda_quandoNaoHaVendaPendente() {
        when(clienteService.buscarPorCpf(CPF)).thenReturn(clienteMock);
        when(repository.findAllByCliente(clienteMock)).thenReturn(Collections.emptyList());

        vendaService.iniciaVenda(CPF);

        verify(repository).save(any(Venda.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando já existe venda INICIADA para o cliente")
    void deveLancarExcecao_quandoJaExisteVendaIniciada() {
        Venda vendaPendente = Venda.criaVenda(clienteMock); // status INICIADA por padrão

        when(clienteService.buscarPorCpf(CPF)).thenReturn(clienteMock);
        when(repository.findAllByCliente(clienteMock)).thenReturn(List.of(vendaPendente));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> vendaService.iniciaVenda(CPF)
        );

        assertThat(ex.getMessage()).contains("Já há uma venda pendete");
        verify(repository, never()).save(any());
    }

    // ----------------------------------------------------------------
    // addCarrinhoVenda
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Deve adicionar produto ao carrinho da venda iniciada")
    void deveAdicionarProdutoAoCarrinho() {
        Venda vendaIniciada = Venda.criaVenda(clienteMock);

        Produto produto = mock(Produto.class);
        var material = mock(com.jpaproject.demo.domain.Material.class);
        when(produto.getMaterial()).thenReturn(material);
        when(material.getQuantidade()).thenReturn(10);
        when(produto.getValor()).thenReturn(java.math.BigDecimal.TEN);

        when(clienteService.buscarPorCpf(CPF)).thenReturn(clienteMock);
        when(repository.buscaVendaIniciada(clienteMock)).thenReturn(vendaIniciada);

        vendaService.addCarrinhoVenda(CPF, produto);

        assertThat(vendaIniciada.getProdutos()).contains(produto);
        verify(repository).save(vendaIniciada);
    }

    // ----------------------------------------------------------------
    // cancelaVenda
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Deve cancelar a venda iniciada do cliente")
    void deveCancelarVenda() {
        Venda vendaIniciada = Venda.criaVenda(clienteMock);

        when(clienteService.buscarPorCpf(CPF)).thenReturn(clienteMock);
        when(repository.buscaVendaIniciada(clienteMock)).thenReturn(vendaIniciada);

        vendaService.cancelaVenda(CPF);

        assertThat(vendaIniciada.getStatusVenda()).isEqualTo(StatusVenda.CANCELADA);
        verify(repository).save(vendaIniciada);
    }

    // ----------------------------------------------------------------
    // finalizaVenda
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Deve finalizar a venda iniciada do cliente")
    void deveFinalizarVenda() {
        Venda vendaIniciada = Venda.criaVenda(clienteMock);

        when(clienteService.buscarPorCpf(CPF)).thenReturn(clienteMock);
        when(repository.buscaVendaIniciada(clienteMock)).thenReturn(vendaIniciada);

        vendaService.finalizaVenda(CPF);

        assertThat(vendaIniciada.getStatusVenda()).isEqualTo(StatusVenda.FINALIZADA);
        verify(repository).save(vendaIniciada);
    }

    // ----------------------------------------------------------------
    // buscaVendaPendente
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Deve retornar a venda pendente do cliente")
    void deveRetornarVendaPendente() {
        Venda vendaIniciada = Venda.criaVenda(clienteMock);

        when(clienteService.buscarPorCpf(CPF)).thenReturn(clienteMock);
        when(repository.buscaVendaIniciada(clienteMock)).thenReturn(vendaIniciada);

        Venda resultado = vendaService.buscaVendaPendente(CPF);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusVenda()).isEqualTo(StatusVenda.INICIADA);
    }

    // ----------------------------------------------------------------
    // buscaVendasPorCliente
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Deve retornar todas as vendas do cliente")
    void deveRetornarTodasAsVendasDoCliente() {
        Venda v1 = Venda.criaVenda(clienteMock);
        Venda v2 = Venda.criaVenda(clienteMock);

        when(clienteService.buscarPorCpf(CPF)).thenReturn(clienteMock);
        when(repository.findAllByCliente(clienteMock)).thenReturn(List.of(v1, v2));

        List<Venda> vendas = vendaService.buscaVendasPorCliente(CPF);

        assertThat(vendas).hasSize(2);
    }
}