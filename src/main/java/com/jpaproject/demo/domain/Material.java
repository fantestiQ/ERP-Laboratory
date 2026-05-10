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
@Table(name = "tb_estoque")
public class Material {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "material_seq")
   @SequenceGenerator(name = "material_seq", sequenceName = "material_sequence")
   private Long id;

   @OneToOne(mappedBy = "material")
   private Produto produto;

   private Integer quantidade;

}
