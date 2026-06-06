package com.jpaproject.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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


    public Produto(Long codigo, String descricao, String categoria, BigDecimal valor) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
    }

    public static Produto criaProduto(Long codigo, String descricao, String categoria, BigDecimal valor){
        Produto produto = new Produto(codigo,descricao,categoria, valor);
        produto.setMaterial(Material.fromProduto(produto));
        return produto;
    }

    public void editaProduto(Produto produtoEditado){
        this.setCodigo(produtoEditado.getCodigo());
        this.setCategoria(produtoEditado.getCategoria());
        this.setDescricao(produtoEditado.getDescricao());
        this.setValor(produtoEditado.getValor());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return  Objects.equals(codigo, produto.codigo) && Objects.equals(descricao, produto.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, descricao);
    }
}
