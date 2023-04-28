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
@Table(schema = "marketplace", name = "cliente")
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(name = "nome")
    private String name;

    @Nonnull
    private String cpf;

    @Nonnull
    @Column(name = "telefone")
    private String telephone;

    @Nonnull
    private String email;
}
