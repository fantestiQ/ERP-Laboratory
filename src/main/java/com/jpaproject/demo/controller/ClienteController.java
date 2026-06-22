package com.jpaproject.demo.controller;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.dtos.PageResponse;
import com.jpaproject.demo.domain.dtos.cliente.ClienteDTO;
import com.jpaproject.demo.domain.dtos.cliente.ClienteResponseDTO;
import com.jpaproject.demo.domain.dtos.endereco.EditaEnderecoDTO;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoDTO;
import com.jpaproject.demo.domain.dtos.endereco.EnderecoResponseDTO;
import com.jpaproject.demo.domain.dtos.endereco.NomeEnderecoDTO;
import com.jpaproject.demo.service.IClienteService;
import com.jpaproject.demo.service.IEnderecoService;
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
@CrossOrigin(origins = "*")
public class ClienteController {

    public final IClienteService clienteService;

    public final IEnderecoService enderecoService;

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteResponseDTO> cadastraCliente(@RequestBody @Valid ClienteDTO dadosCliente){
        Cliente cliente = Cliente.criaCliente(dadosCliente);
        ClienteResponseDTO response = new ClienteResponseDTO(clienteService.salvar(cliente));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return  ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscaClienteCPF(@PathVariable String cpf){
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        if (cliente== null)
            return ResponseEntity.notFound().build();
        ClienteResponseDTO response = new ClienteResponseDTO(cliente);
        return  ResponseEntity.ok(response);
    }

    //Não deleta por conta da integridade com a FK na VENDA

    @DeleteMapping("/{cpf}")
    public ResponseEntity deletaCliente(@PathVariable String cpf){
       clienteService.remover(cpf);
       return   ResponseEntity.noContent().build();
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> editaCliente(@RequestBody @Valid ClienteDTO dados,@PathVariable String cpf ){
        Cliente clienteDados = Cliente.criaCliente(dados);
        ClienteResponseDTO response = new ClienteResponseDTO(clienteService.editar(cpf,clienteDados));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ClienteResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "primeiroNome", direction =  Sort.Direction.ASC)
            Pageable pageable){
        return ResponseEntity.ok(clienteService.listarTodos(pageable));
    }

    @PostMapping("/{cpf}/enderecos")
    public ResponseEntity<EnderecoResponseDTO> adicionaEnderecoCliente(@PathVariable String cpf,
                                                                       @RequestBody @Valid EnderecoDTO dadosEndereco)
    {
        EnderecoResponseDTO response =
                new EnderecoResponseDTO(enderecoService.adicionaEnderecoCliente(cpf,dadosEndereco));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return  ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{cpf}/enderecos")
    public ResponseEntity<EnderecoResponseDTO> editaEnderecoCliente(@PathVariable String cpf,
                                                 @RequestBody @Valid EditaEnderecoDTO dados){
        EnderecoResponseDTO response = new EnderecoResponseDTO(enderecoService.editarEndereco(dados.enderecoEditar(),cpf,dados.dadosEndereco()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cpf}/enderecos")
    public ResponseEntity deletaEnderecoCliente(@PathVariable String cpf,
                                                @RequestBody @Valid NomeEnderecoDTO dado){
        enderecoService.removerEndereco(dado.endereco(), cpf);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{cpf}/endereco")
    public ResponseEntity<EnderecoResponseDTO> buscaEnderecoCliente(@PathVariable String cpf,
                                                                    @RequestParam String  endereco){
        EnderecoResponseDTO response = new EnderecoResponseDTO(enderecoService.buscarEndereco(endereco,cpf));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cpf}/enderecos")
    public ResponseEntity<PageResponse<EnderecoResponseDTO>> buscaEnderecosCliente(@PathVariable String cpf,
                                                                                   @PageableDefault(
                                                                                           size = 20,
                                                                                           sort = "endereco",
                                                                                           direction =  Sort.Direction.ASC)
                                                                                   Pageable pageable){
        return ResponseEntity.ok(enderecoService.listaTodosEnderecos(cpf,pageable));
    }

}
