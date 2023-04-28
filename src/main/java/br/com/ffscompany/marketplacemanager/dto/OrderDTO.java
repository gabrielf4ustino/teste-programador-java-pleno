package br.com.ffscompany.marketplacemanager.dto;

import br.com.ffscompany.marketplacemanager.model.ProductModel;

import java.sql.Date;
import java.util.List;

public record OrderDTO(Date dateOfIssue, String description, List<ProductModel> products, Long amount) {
}
