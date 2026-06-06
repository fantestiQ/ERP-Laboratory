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
@Table(name = "tb_estoque")
public class Material {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "material_seq")
   @SequenceGenerator(name = "material_seq", sequenceName = "material_sequence")
   private Long id;

   @OneToOne(mappedBy = "material")
   private Produto produto;

   private Integer quantidade;

   public Material(Produto produto) {
      this.produto = produto;
      this.quantidade = 0;
   }

   public static  Material fromProduto(Produto produto){
      return new Material(produto);
   }

   @Override
   public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      Material material = (Material) o;
      return Objects.equals(produto, material.produto);
   }

}
