package com.jpaproject.demo;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;
import com.jpaproject.demo.service.IClienteService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class ClienteTest {

    @Autowired
    IClienteService clienteService;

    Cliente cliente = null;
    Cliente cliente2 = null;
    Cliente cliente3 = null;


    @Test
    public void deveSalvarCliente(){
       Cliente clienteSalvo = cliente;
       Assertions.assertNotNull(clienteSalvo);
       Cliente clienteBuscado = clienteService.buscarPorCpf(cliente.getCpf());
       Assertions.assertEquals(clienteSalvo, clienteBuscado);
    }

    @Test
    public void deveBuscarClientePorCpf(){
        Cliente clienteBuscado = clienteService.buscarPorCpf(cliente.getCpf());
        Assertions.assertNotNull(clienteBuscado);
    }

    @Test
    public void deveEditarCliente(){
        Cliente clienteEditado =  new Cliente(
                ">.<@teste.com",
                "Isac",
                "dos Santos",
                "124321454"
        );
        Cliente clienteBuscado =clienteService.editar(cliente.getCpf(), clienteEditado);
        Assertions.assertNotNull(clienteBuscado);

        Assertions.assertAll("clienteEditado",
                ()-> assertEquals(clienteEditado.getEmail(), clienteBuscado.getEmail()),
                ()-> assertEquals(clienteEditado.getPrimeiroNome(), clienteBuscado.getPrimeiroNome()),
                ()-> assertEquals(clienteEditado.getLastName(), clienteBuscado.getLastName()),
                ()-> assertEquals(clienteEditado.getCpf(), clienteBuscado.getCpf())
                );
    }

    @Test
    public void deveRemoverClientePorCpf(){
       Cliente clienteBuscado = clienteService.buscarPorCpf(cliente.getCpf());
       Assertions.assertNotNull(clienteBuscado);
       clienteService.remover(cliente.getCpf());
       clienteBuscado = clienteService.buscarPorCpf(cliente.getCpf());
       Assertions.assertNull(clienteBuscado);
    }

    @Test
    public void deveListarTodos(){
        List<Cliente> insertList = new ArrayList<>();
        insertList.add(cliente2);
        insertList.add(cliente3);

        insertList.forEach(c -> clienteService.salvar(c));

        insertList.add(cliente);

        List<Cliente> clienteList = clienteService.listarTodos();

        Assertions.assertEquals(insertList.size(),clienteList.size());
        Assertions.assertTrue(clienteList.containsAll(insertList));

    }

    @Test
    public void devePersistirClienteEEndereco(){
        Endereco endereco = new Endereco("Rua das Elevações","0211890", "São Paulo",321,"SP",cliente);
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        cliente.setEnderecos(enderecos);

        Cliente cliTest = clienteService.salvar(cliente);
        assertNotNull(cliTest.getEnderecos());

    }

    @Test
    public void deveAdicionarSaldoAoCliente(){
        Cliente c = clienteService.buscarPorCpf(cliente.getCpf());
        c.setSaldo("30");
        assertEquals(BigDecimal.valueOf(30),
                c.getSaldo());
       Cliente cliTest = clienteService.salvar(c);
       assertEquals(cliTest.getSaldo(), c.getSaldo());
    }



    @BeforeEach
    public void init(){
        cliente = new Cliente(
                ">.<@teste.com",
                "Gyro",
                "Zeppeli",
                "1412312311"
        );

        cliente2 = new Cliente(
                ":P@teste.com",
                "Johnny",
                "Joestar",
                "141212315"
        );
        cliente3 = new Cliente(
                ":]@teste.com",
                "Isac",
                "dos Santos",
                "125311567"
        );


        clienteService.salvar(cliente);
    }

    @AfterEach
    public void end(){
        List<Cliente> clienteList = clienteService.listarTodos();
        clienteList.forEach(e -> clienteService.remover(e.getCpf()));
    }

}
