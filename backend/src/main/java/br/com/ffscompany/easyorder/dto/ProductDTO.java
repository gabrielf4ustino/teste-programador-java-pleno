package br.com.ffscompany.easyorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(

        Long id,

        @NotBlank String description,

        @NotNull String unit,

        @NotNull BigDecimal price) {
}
