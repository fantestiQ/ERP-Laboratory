package com.jpaproject.demo.domain.dtos.vendas;

import com.jpaproject.demo.domain.Produto;
import com.jpaproject.demo.domain.StatusVenda;
import com.jpaproject.demo.domain.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public record VendaResponseDTO(
        Long id,
        List<ItemVendaResponseDTO> produtos,
        String nomeCliente,
        String cpfCliente,
        BigDecimal valorTotal,
        StatusVenda statusVenda,
        LocalDateTime dataVenda,
        UUID codVenda
) {
    public VendaResponseDTO (Venda venda){
        this(venda.getId(),listaProdutos(venda),venda.getCliente().getPrimeiroNome(),
                venda.getCliente().getCpf(),venda.getValorTotal(),
                venda.getStatusVenda(),venda.getDataVenda(),venda.getCodVenda());
    }

    public static List<ItemVendaResponseDTO> listaProdutos(Venda venda){
        return venda.getProdutos().stream().map(ItemVendaResponseDTO::new).toList();
    }
}
