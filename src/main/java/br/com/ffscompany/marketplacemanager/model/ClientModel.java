package br.com.ffscompany.marketplacemanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String name;

    private String cpf;

    @Column(name = "telefone")
    private String telephone;

    private String email;
}
