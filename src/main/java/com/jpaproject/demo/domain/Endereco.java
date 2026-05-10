package com.jpaproject.demo.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
