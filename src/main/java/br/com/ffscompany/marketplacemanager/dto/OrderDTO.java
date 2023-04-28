package br.com.ffscompany.marketplacemanager.dto;

import br.com.ffscompany.marketplacemanager.model.ProductModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderDTO(
        String description,

        @NotBlank List<ProductModel> products) {
}
