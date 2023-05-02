package br.com.ffscompany.easyorder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientDTO(

        Long id,

        @NotBlank String name,

        @NotBlank @Pattern(regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$", message = "CPF inválido") String cpf,

        @NotBlank @Pattern(regexp = "^(\\(\\d{2}\\)\\s)?\\d{4,5}-\\d{4}(?!\\d)$", message = "Telefone inválido") String telephone,

        @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email inválido") String email) {
}
