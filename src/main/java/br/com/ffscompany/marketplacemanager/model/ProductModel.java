package br.com.ffscompany.marketplacemanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "descricao")
    private String description;

    @Column(name = "unidade")
    private Integer unit;

    @Column(name = "valor")
    private Long price;
}
