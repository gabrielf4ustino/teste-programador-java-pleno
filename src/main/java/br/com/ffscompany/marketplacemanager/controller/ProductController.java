package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.ProductDTO;
import br.com.ffscompany.marketplacemanager.model.ProductModel;
import br.com.ffscompany.marketplacemanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("cadastro")
    public void productRegister(@PathVariable ProductDTO request){
        productService.register(request);
    }

    @GetMapping("todos")
    public List<ProductModel> findAllProducts(){
        return productService.findAllProducts();
    }
}
