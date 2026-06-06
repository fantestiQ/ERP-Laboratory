package com.jpaproject.demo.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "tb_enderecos")
public class Endereco {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "endereco_seq")
    @SequenceGenerator(name = "Endereco_seq", sequenceName = "endereco_sequence", allocationSize = 50)
    private Long id;
    private String endereco;
    private String cep;
    private String cidade;
    private Integer numero;
    private String uf;

    @ManyToOne
    private Cliente cliente;

    public Endereco(String endereco, String cep, String cidade, Integer numero, String uf, Cliente cliente) {
        this.endereco = endereco;
        this.cep = cep;
        this.cidade = cidade;
        this.numero = numero;
        this.uf = uf;
        this.cliente = cliente;
    }

    public void editaEndereco(Endereco enderecoEditado){
        this.setEndereco(enderecoEditado.getEndereco());
        this.setUf(enderecoEditado.getUf());
        this.setCep(enderecoEditado.getCep());
        this.setCidade(enderecoEditado.getCidade());
        this.setNumero(enderecoEditado.getNumero());
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", endereco='" + endereco + '\'' +
                ", cep='" + cep + '\'' +
                ", cidade='" + cidade + '\'' +
                ", numero=" + numero +
                ", uf='" + uf + '\'' +
                ", cliente=" + cliente +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco1 = (Endereco) o;
        return Objects.equals(id, endereco1.id) && Objects.equals(endereco, endereco1.endereco) && Objects.equals(cliente, endereco1.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endereco, cliente);
    }
}
