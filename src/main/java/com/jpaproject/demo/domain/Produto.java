package com.jpaproject.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "produto_seq")
    @SequenceGenerator(name = "produto_seq", sequenceName = "produto_sequence", allocationSize = 50)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long codigo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private BigDecimal valor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_material", referencedColumnName = "id")
    private Material material;

    @ManyToMany(mappedBy = "produtos")
    private List<Venda> venda;



}
