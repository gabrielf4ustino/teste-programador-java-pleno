package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.OrderDTO;
import br.com.ffscompany.marketplacemanager.model.OrderModel;
import br.com.ffscompany.marketplacemanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedido")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("registro")
    public void orderRegister(@PathVariable OrderDTO request){
        orderService.register(request);
    }

    @GetMapping("todos")
    public List<OrderModel> findAllOrders(){
        return orderService.findAllOrders();
    }
}
