package com.jpaproject.demo;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.repository.ClienteRepository;
import com.jpaproject.demo.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private static final String CPF = "1412312311";

    @BeforeEach
    void setUp() {
        cliente = new Cliente(
                ":p@teste.com",
                "Gyro I",
                "Zeppeli I",
                CPF
        );
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    void deveSalvarCliente() {
        when(repository.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteService.salvar(cliente);

        assertThat(clienteSalvo).isEqualTo(cliente);
        verify(repository).save(cliente);
    }

    @Test
    @DisplayName("Deve buscar cliente por CPF")
    void deveBuscarClientePorCpf() {
        when(repository.findByCpf(CPF)).thenReturn(Optional.of(cliente));

        Cliente clienteBuscado = clienteService.buscarPorCpf(CPF);

        assertThat(clienteBuscado).isNotNull();
        assertThat(clienteBuscado.getCpf()).isEqualTo(CPF);
    }

    @Test
    @DisplayName("Deve retornar null ao buscar CPF inexistente")
    void deveRetornarNull_quandoCpfNaoExiste() {
        when(repository.findByCpf("000")).thenReturn(Optional.empty());

        Cliente clienteBuscado = clienteService.buscarPorCpf("000");

        assertThat(clienteBuscado).isNull();
    }

    @Test
    @DisplayName("Deve editar um cliente existente")
    void deveEditarCliente() {
        Cliente clienteEditado = new Cliente(
                ">.<@teste.com",
                "Isac",
                "dos Santos",
                "124321454"
        );

        when(repository.findByCpf(CPF)).thenReturn(Optional.of(cliente));
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente clienteBuscado = clienteService.editar(CPF, clienteEditado);

        assertThat(clienteBuscado).isNotNull();
        assertThat(clienteBuscado.getEmail()).isEqualTo(clienteEditado.getEmail());
        assertThat(clienteBuscado.getPrimeiroNome()).isEqualTo(clienteEditado.getPrimeiroNome());
        assertThat(clienteBuscado.getLastName()).isEqualTo(clienteEditado.getLastName());
        assertThat(clienteBuscado.getCpf()).isEqualTo(clienteEditado.getCpf());
    }

    @Test
    @DisplayName("Não deve editar quando cliente não existe")
    void naoDeveEditar_quandoClienteNaoExiste() {
        when(repository.findByCpf("000")).thenReturn(Optional.empty());

        Cliente resultado = clienteService.editar("000", cliente);

        assertThat(resultado).isNull();
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover cliente por CPF")
    void deveRemoverClientePorCpf() {
        when(repository.findByCpf(CPF)).thenReturn(Optional.of(cliente));

        clienteService.remover(CPF);

        verify(repository).delete(cliente);
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void deveListarTodos() {
        Cliente cliente2 = new Cliente(":P@teste.com", "Johnny", "Joestar", "141212315");
        Cliente cliente3 = new Cliente(":]@teste.com", "Isac", "dos Santos", "125311567");

        when(repository.findAll()).thenReturn(List.of(cliente, cliente2, cliente3));

        List<Cliente> clienteList = clienteService.listarTodos();

        assertThat(clienteList).hasSize(3);
        assertThat(clienteList).containsExactlyInAnyOrder(cliente, cliente2, cliente3);
    }

    @Test
    @DisplayName("Deve adicionar saldo ao cliente")
    void deveAdicionarSaldoAoCliente() {
        cliente.setSaldo("30");

        assertThat(cliente.getSaldo()).isEqualTo(BigDecimal.valueOf(30));

        when(repository.save(cliente)).thenReturn(cliente);
        Cliente clienteSalvo = clienteService.salvar(cliente);

        assertThat(clienteSalvo.getSaldo()).isEqualTo(cliente.getSaldo());
    }
}