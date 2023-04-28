package br.com.ffscompany.marketplacemanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        @NotBlank String description,

        @NotNull Integer unit,

        @NotNull Double price) {
}
