package com.jpaproject.demo.repository;

import com.jpaproject.demo.domain.Cliente;
import com.jpaproject.demo.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByClienteId(Long id);
}
