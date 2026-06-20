package com.jpaproject.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVenda statusVenda;

    private LocalDateTime dataVenda;

    @Column(name = "cod_venda", unique = true, nullable = false)
    private UUID codVenda;

    public Venda(Cliente cliente) {
        this.produtos = new ArrayList<>();
        this.cliente = cliente;
        this.valorTotal = BigDecimal.ZERO;
        this.codVenda = UUID.randomUUID();
        this.dataVenda = LocalDateTime.now();
        this.statusVenda = StatusVenda.INICIADA;
    }

    public static Venda criaVenda(Cliente cli){
       return new Venda(cli);
    }

    public void adicionarAoCarrinho(Produto produto){
        if (produto.getMaterial().getQuantidade()>0){
        this.produtos.add(produto);
        this.valorTotal = valorTotal.add(produto.getValor());
        produto.getMaterial().setQuantidade(produto.getMaterial().getQuantidade() - 1);
        }else throw new IllegalArgumentException("Produto está com estoque zerado!");
    }
    public void finalizarVenda(){
        this.statusVenda = StatusVenda.FINALIZADA;
    }
    public void cancelarVenda(){
        this.statusVenda = StatusVenda.CANCELADA;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(codVenda, venda.codVenda);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codVenda);
    }
}
