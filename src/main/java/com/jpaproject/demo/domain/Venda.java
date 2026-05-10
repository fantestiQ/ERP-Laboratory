package com.jpaproject.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_venda")
    @SequenceGenerator(name = "sq_venda", sequenceName = "venda_sequence")
    private Long id;

    @ManyToMany
    @JoinTable(name = "tb_produto_venda",
            joinColumns = @JoinColumn(name = "venda_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "cod_venda", unique = true, nullable = false)
    private UUID codVenda;
}
