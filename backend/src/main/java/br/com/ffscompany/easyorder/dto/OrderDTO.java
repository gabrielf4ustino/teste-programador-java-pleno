package br.com.ffscompany.easyorder.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public record OrderDTO(

        Long id,

        Date issueDate,

        String description,

        @NotNull ClientDTO client,

        @NotNull List<ProductDTO> products,

        BigDecimal amount) {
}
