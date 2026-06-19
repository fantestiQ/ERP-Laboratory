package com.jpaproject.demo;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoDTO;
import com.jpaproject.demo.repository.EnderecoRepository;
import com.jpaproject.demo.service.EnderecoService;
import com.jpaproject.demo.service.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoTest {

    @Mock
    private EnderecoRepository repository;

    @Mock
    private IClienteService clienteService;

    @InjectMocks
    private EnderecoService enderecoService;

    private Cliente cliente;
    private Endereco primeiroEndereco;
    private Endereco segundoEndereco;
    private static final String CPF = "1412312311";

    @BeforeEach
    void setUp() throws Exception {
        cliente = new Cliente(
                ">.<@teste.com",
                "Gyro",
                "Zeppeli",
                CPF
        );
        // o id é gerado pelo JPA; setamos via reflection para simular um cliente persistido
        setId(cliente, 1L);

        primeiroEndereco = new Endereco("Rua das Elevações", "0211890", "São Paulo", 321, "SP", cliente);
        segundoEndereco = new Endereco("Avenida Stars", "0211780", "Rio de Janeiro", 3211, "RJ", cliente);

        cliente.setEnderecos(new ArrayList<>(List.of(primeiroEndereco, segundoEndereco)));
    }

    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    @DisplayName("Deve buscar um endereço específico do cliente")
    void deveBuscarEnderecoCliente() {
        when(clienteService.buscarPorCpf(CPF)).thenReturn(cliente);
        when(repository.findByClienteId(cliente.getId())).thenReturn(cliente.getEnderecos());

        Endereco endereco = enderecoService.buscarEndereco("Rua das Elevações", CPF);

        assertThat(endereco).isEqualTo(primeiroEndereco);
    }

    @Test
    @DisplayName("Deve retornar null ao buscar endereço inexistente")
    void deveRetornarNull_quandoEnderecoNaoExiste() {
        when(clienteService.buscarPorCpf(CPF)).thenReturn(cliente);
        when(repository.findByClienteId(cliente.getId())).thenReturn(cliente.getEnderecos());

        Endereco endereco = enderecoService.buscarEndereco("Rua Inexistente", CPF);

        assertThat(endereco).isNull();
    }

    @Test
    @DisplayName("Deve listar todos os endereços do cliente")
    void deveBuscarTodosEnderecosCliente() {
        when(clienteService.buscarPorCpf(CPF)).thenReturn(cliente);
        when(repository.findByClienteId(cliente.getId())).thenReturn(cliente.getEnderecos());

        List<Endereco> enderecos = enderecoService.listaTodosEnderecos(CPF);

        assertThat(enderecos).containsExactlyInAnyOrder(primeiroEndereco, segundoEndereco);
    }

    @Test
    @DisplayName("Deve editar um endereço do cliente")
    void deveEditarEnderecoCliente() {
        when(clienteService.buscarPorCpf(CPF)).thenReturn(cliente);
        when(repository.findByClienteId(cliente.getId())).thenReturn(cliente.getEnderecos());
        when(repository.save(any(Endereco.class))).thenAnswer(inv -> inv.getArgument(0));

        EnderecoDTO enderecoEditado = new EnderecoDTO("Avenida Bringel", "0211890", "Meu coração", 321, "SP");

        Endereco resultado = enderecoService.editarEndereco("Rua das Elevações", CPF, enderecoEditado);

        assertThat(resultado.getEndereco()).isEqualTo("Avenida Bringel");
        assertThat(resultado.getCidade()).isEqualTo("Meu coração");
        verify(repository).save(primeiroEndereco);
    }

    @Test
    @DisplayName("Deve remover um endereço do cliente")
    void deveRemoverEnderecoCliente() {
        when(clienteService.buscarPorCpf(CPF)).thenReturn(cliente);
        when(repository.findByClienteId(cliente.getId())).thenReturn(cliente.getEnderecos());

        enderecoService.removerEndereco("Rua das Elevações", CPF);

        verify(repository).delete(primeiroEndereco);
        verify(clienteService).salvar(cliente);
        assertThat(cliente.getEnderecos()).containsExactly(segundoEndereco);
    }

    @Test
    @DisplayName("Não deve fazer nada ao remover endereço de cliente inexistente")
    void naoDeveRemoverEndereco_quandoClienteNaoExiste() {
        when(clienteService.buscarPorCpf("000")).thenReturn(null);

        enderecoService.removerEndereco("Rua das Elevações", "000");

        verify(repository, never()).delete(any());
        verify(clienteService, never()).salvar(any());
    }
}