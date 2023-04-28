package br.com.ffscompany.marketplacemanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_de_emissao")
    private Date DateOfIssue;

    @Column(name = "descricao")
    private String description;

    @OneToMany
    @Column(name = "produtos")
    private List<ProductModel> products;

    @Column(name = "valor_total")
    private Long amount;
}
