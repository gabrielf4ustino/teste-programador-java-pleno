package br.com.ffscompany.marketplacemanager.service;

import br.com.ffscompany.marketplacemanager.dto.ProductDTO;
import br.com.ffscompany.marketplacemanager.model.ProductModel;
import br.com.ffscompany.marketplacemanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void register(ProductDTO productToRegister){
        ProductModel productModel =  new ProductModel(
                null,
                productToRegister.description(),
                productToRegister.unit(),
                productToRegister.price()
        );
        productRepository.save(productModel);
    }

    public List<ProductModel> findAllProducts(){
        return productRepository.findAll();
    }
}
