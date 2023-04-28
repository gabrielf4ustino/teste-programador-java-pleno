package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.ProductDTO;
import br.com.ffscompany.marketplacemanager.model.ProductModel;
import br.com.ffscompany.marketplacemanager.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produto")
@Transactional
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("cadastro")
    public void productRegister(@RequestBody @Valid ProductDTO product){
        productService.register(product);
    }

    @GetMapping("todos")
    public List<ProductModel> findAllProducts(){
        return productService.findAllProducts();
    }
}
