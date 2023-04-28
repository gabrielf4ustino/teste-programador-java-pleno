package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.OrderDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedido")
public class OrderController {

    @PostMapping("registro")
    public void orderRegister(@PathVariable OrderDto request){

    }
}
