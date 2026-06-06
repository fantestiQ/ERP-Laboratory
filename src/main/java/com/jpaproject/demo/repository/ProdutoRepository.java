package com.jpaproject.demo.repository;

import com.jpaproject.demo.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    Produto findByCodigo(Long cod);
}
