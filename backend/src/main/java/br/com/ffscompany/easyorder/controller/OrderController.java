package br.com.ffscompany.easyorder.controller;

import br.com.ffscompany.easyorder.dto.ClientDTO;
import br.com.ffscompany.easyorder.dto.OrderDTO;
import br.com.ffscompany.easyorder.dto.ProductDTO;
import br.com.ffscompany.easyorder.model.OrderModel;
import br.com.ffscompany.easyorder.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("pedido")
@Transactional // Defines that all operations in this class must be executed within a transaction
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class OrderController {

    @Autowired // Injects the OrderService
    private OrderService orderService;

    /**
     * Endpoint to register a new order.
     *
     * @param order      An OrderDTO object containing the data of the order to be registered
     * @param uriBuilder A UriComponentsBuilder object used to build the response URL
     * @return A ResponseEntity object containing the HTTP response status and, if successful,
     * an OrderDTO object containing the data of the registered order
     */
    @PostMapping("registro")
    public ResponseEntity<OrderDTO> orderRegister(@RequestBody @Valid OrderDTO order, UriComponentsBuilder uriBuilder) {
        OrderModel registeredOrder = orderService.register(order);
        return getResponseEntity(uriBuilder, registeredOrder);
    }

    /**
     * Endpoint to edit an existing order.
     *
     * @param order      An OrderDTO object containing the data of the order to be edited
     * @param uriBuilder A UriComponentsBuilder object used to build the response URL
     * @return A ResponseEntity object containing the HTTP response status and, if successful,
     * an OrderDTO object containing the data of the edited order
     */
    @PutMapping("editar")
    public ResponseEntity editOrder(@RequestBody @Valid OrderDTO order, UriComponentsBuilder uriBuilder) {
        Optional<OrderModel> editedOrder = orderService.edit(order);
        if (editedOrder.isPresent()) {
            return getResponseEntity(uriBuilder, editedOrder.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Helper method to build the response entity with the appropriate status and body.
     *
     * @param uriBuilder A UriComponentsBuilder object used to build the response URL
     * @param orderModel An OrderModel object containing the data of the order to be returned in the response
     * @return A ResponseEntity object containing the HTTP response status and an OrderDTO object
     * containing the data of the specified order
     */
    private static ResponseEntity<OrderDTO> getResponseEntity(UriComponentsBuilder uriBuilder, OrderModel orderModel) {
        return ResponseEntity
                .created(uriBuilder.path("/pedido/{id}").buildAndExpand(orderModel.getId()).toUri())
                .body(new OrderDTO(
                        orderModel.getId(),
                        orderModel.getIssueDate(),
                        orderModel.getDescription(),
                        new ClientDTO(orderModel.getClient().getId(),
                                orderModel.getClient().getName(),
                                orderModel.getClient().getCpf(),
                                orderModel.getClient().getTelephone(),
                                orderModel.getClient().getEmail()),
                        orderModel.getProducts().stream().map(productModel -> new ProductDTO(
                                productModel.getId(),
                                productModel.getDescription(),
                                productModel.getUnit(),
                                productModel.getPrice()
                        )).toList(),
                        orderModel.getAmount()));
    }

    /**
     * Endpoint to delete an order by its ID.
     *
     * @param id The ID of the order to be deleted
     * @return A ResponseEntity object containing the HTTP response status
     */
    @DeleteMapping("remover/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        orderService.remove(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to retrieve all registered orders.
     *
     * @return A ResponseEntity object containing the HTTP response status and, if successful,
     * a list of OrderDTO objects containing the data of all registered orders
     */
    @GetMapping("todos")
    public ResponseEntity<List<OrderDTO>> findAllOrders() {
        List<OrderDTO> orderDTOS = orderService.findAll().stream().map(orderModel -> new OrderDTO(
                orderModel.getId(),
                orderModel.getIssueDate(),
                orderModel.getDescription(),
                new ClientDTO(orderModel.getClient().getId(),
                        orderModel.getClient().getName(),
                        orderModel.getClient().getCpf(),
                        orderModel.getClient().getTelephone(),
                        orderModel.getClient().getEmail()),
                orderModel.getProducts().stream().map(productModel -> new ProductDTO(
                        productModel.getId(),
                        productModel.getDescription(),
                        productModel.getUnit(),
                        productModel.getPrice()
                )).toList(),
                orderModel.getAmount())).toList();
        return ResponseEntity.ok(orderDTOS);
    }


    /**
     * Endpoint to search for an order by its ID.
     *
     * @param id ID of the order to be searched
     * @return ResponseEntity object containing the HTTP response status and, if successful,
     * an OrderDTO object containing the data of the found order
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id) {
        Optional<OrderModel> orderModel = orderService.findById(id);
        if (orderModel.isPresent()) {
            OrderDTO orderDTO = new OrderDTO(
                    orderModel.get().getId(),
                    orderModel.get().getIssueDate(),
                    orderModel.get().getDescription(),
                    new ClientDTO(orderModel.get().getClient().getId(),
                            orderModel.get().getClient().getName(),
                            orderModel.get().getClient().getCpf(),
                            orderModel.get().getClient().getTelephone(),
                            orderModel.get().getClient().getEmail()),
                    orderModel.get().getProducts().stream().map(productModel -> new ProductDTO(
                            productModel.getId(),
                            productModel.getDescription(),
                            productModel.getUnit(),
                            productModel.getPrice()
                    )).toList(),
                    orderModel.get().getAmount());
            return ResponseEntity.ok(orderDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
