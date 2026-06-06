package com.jpaproject.demo;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;
import com.jpaproject.demo.repository.EnderecoRepository;
import com.jpaproject.demo.service.IClienteService;
import com.jpaproject.demo.service.IEnderecoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EnderecoTest {

    @Autowired
    IEnderecoService enderecoService;

    @Autowired
    IClienteService clienteService;

    Cliente cliente = null;

    @Test
    public void deveEditarEnderecoCliente(){
        Endereco endereco = enderecoService.buscarEndereco("Rua das Elevações", cliente.getCpf());
        endereco.setEndereco("Avenida Bringel");
        endereco.setCidade("Meu coração");

       Endereco enderecoEditado = enderecoService.editarEndereco("Rua das Elevações", cliente.getCpf(),endereco);
       Assertions.assertEquals(endereco, enderecoEditado);
    }

    @Test
    public void deveRemoverEnderecoCliente(){
        List<Endereco> enderecos = enderecoService.listaTodosEnderecos(cliente.getCpf());
        Assertions.assertTrue(enderecos.containsAll(cliente.getEnderecos()));
        enderecoService.removerEndereco("Rua das Elevações", cliente.getCpf());

        Optional<Endereco> enderecoOp =  enderecos.stream().filter(e -> e.getEndereco().equals("Rua das Elevações")).findFirst();
        Endereco endereco = enderecoOp.orElse(null);
        cliente.getEnderecos().remove(endereco);

        enderecos = enderecoService.listaTodosEnderecos(cliente.getCpf());

        Assertions.assertTrue(cliente.getEnderecos().containsAll(enderecos));
    }

    @Test
    public void deveBuscarEnderecoCliente(){
      Endereco endereco =  enderecoService.buscarEndereco("Rua das Elevações", cliente.getCpf());
        Assertions.assertEquals(cliente.getEnderecos().get(0),endereco);
    }

    @Test
    public void deveBuscarTodosEnderecosCliente(){
        List<Endereco> enderecos = enderecoService.listaTodosEnderecos(cliente.getCpf());
        Assertions.assertArrayEquals(cliente.getEnderecos().toArray(),enderecos.toArray());
    }


    @BeforeEach
    public void init(){
        cliente = new Cliente(
                ">.<@teste.com",
                "Gyro",
                "Zeppeli",
                "1412312311"
        );

        Endereco primeiroEndereco = new Endereco("Rua das Elevações","0211890", "São Paulo",321,"SP",cliente);
        Endereco segundoEndereco = new Endereco("Avenida Stars","0211780", "Rio de Janeiro",3211,"RJ",cliente);

        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(primeiroEndereco);
        enderecos.add(segundoEndereco);
        cliente.setEnderecos(enderecos);

        clienteService.salvar(cliente);

    }

    @AfterEach
    public void end(){
        List<Cliente> clienteList = clienteService.listarTodos();
        clienteList.forEach(e -> clienteService.remover(e.getCpf()));
    }
}
