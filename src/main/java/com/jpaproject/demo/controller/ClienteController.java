package com.jpaproject.demo.controller;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.cliente.ClienteDTO;
import com.jpaproject.demo.domain.dtos.cliente.ClienteResponseDTO;
import com.jpaproject.demo.service.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    public final IClienteService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteResponseDTO> cadastraCliente(@RequestBody @Valid ClienteDTO dadosCliente){
        Cliente cliente = Cliente.criaCliente(dadosCliente);
        ClienteResponseDTO response = new ClienteResponseDTO(service.salvar(cliente));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return  ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscaClienteCPF(@PathVariable String cpf){
        Cliente cliente = service.buscarPorCpf(cpf);
        if (cliente== null)
            return ResponseEntity.notFound().build();
        ClienteResponseDTO response = new ClienteResponseDTO(cliente);
        return  ResponseEntity.ok(response);
    }

    //Não deleta por conta da integridade com a FK na VENDA

    @DeleteMapping("/{cpf}")
    public ResponseEntity deletaCliente(@PathVariable String cpf){
       service.remover(cpf);
       return   ResponseEntity.noContent().build();
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> editaCliente(@RequestBody @Valid ClienteDTO dados,@PathVariable String cpf ){
        Cliente clienteDados = Cliente.criaCliente(dados);
        ClienteResponseDTO response = new ClienteResponseDTO(service.editar(cpf,clienteDados));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ClienteResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "primeiroNome", direction =  Sort.Direction.ASC)
            Pageable pageable){
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

}
