package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.OrderDTO;
import br.com.ffscompany.marketplacemanager.model.OrderModel;
import br.com.ffscompany.marketplacemanager.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedido")
@Transactional
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("registro")
    public void orderRegister(@RequestBody @Valid OrderDTO order){
        orderService.register(order);
    }

    @GetMapping("todos")
    public List<OrderModel> findAllOrders(){
        return orderService.findAllOrders();
    }
}
