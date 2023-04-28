package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.ProductDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("produto")
public class ProductController {

    @PostMapping("cadastro")
    public void productRegister(@PathVariable ProductDto request){

    }
}
