package br.com.ffscompany.marketplacemanager.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(schema = "marketplace", name = "produto")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(name = "descricao")
    private String description;

    @Nonnull
    @Column(name = "unidade")
    private Integer unit;

    @Nonnull
    @Column(name = "valor")
    private Long price;
}
