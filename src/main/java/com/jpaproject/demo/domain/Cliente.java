package com.jpaproject.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "tb_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_sequence", allocationSize = 50)
    private Long id;

    @Column(nullable = false)
    private String primeiroNome;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 13)
    private String cpf;

    @Column(nullable = false, length = 50)
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "cliente")
    private List<Venda> vendas;

    private BigDecimal saldo;

    public void setSaldo(String saldo) {
        this.saldo = new BigDecimal(saldo);
    }

    public Cliente(String email, String nome, String lastName, String cpf) {
        this.email = email;
        this.primeiroNome = nome;
        this.cpf = cpf;
        this.lastName = lastName;
        this.saldo = BigDecimal.ZERO;
    }

    public void editaCliente(Cliente clienteEditado){
        this.setCpf(clienteEditado.getCpf());
        this.setPrimeiroNome(clienteEditado.getPrimeiroNome());
        this.setLastName(clienteEditado.getLastName());
        this.setEmail(clienteEditado.getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(primeiroNome, cliente.primeiroNome) && Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primeiroNome, cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", primeiroNome='" + primeiroNome + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
