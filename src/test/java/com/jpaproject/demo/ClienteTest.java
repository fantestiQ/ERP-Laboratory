package com.jpaproject.demo;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.service.IClienteService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class ClienteTest {

    @Autowired
    IClienteService clienteService;

    Cliente cliente = new Cliente(
            ">.<@teste.com",
            "Isac",
            "dos Santos",
            "1419426781"
    );

    @Test
    public void deveSalvarCliente(){
        clienteService.salvar(cliente);
    }

    @Test
    public void deveBuscarClientePorCpf(){
        Cliente clienteBuscado = clienteService.buscarPorCpf(cliente.getCpf());
        System.out.println(clienteBuscado);
    }

    @Test
    public void deveEditarCliente(){
        Cliente clienteEditado =  new Cliente(
                ">.<@teste.com",
                "Isac",
                "dos Santos",
                "124321454"
        );
        clienteService.editar(cliente.getCpf(), clienteEditado);
    }

    @Test
    public void deveRemoverClientePorCpf(){
        clienteService.remover(cliente.getCpf());
    }

    @Test
    public void deveListarTodos(){
       List<Cliente> clienteList = clienteService.listarTodos();
    }

    @AfterEach
    public void end(){
        List<Cliente> clienteList = clienteService.listarTodos();
    }

}
