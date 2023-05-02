package br.com.ffscompany.easyorder;

import br.com.ffscompany.easyorder.dto.ClientDTO;
import br.com.ffscompany.easyorder.dto.OrderDTO;
import br.com.ffscompany.easyorder.dto.ProductDTO;
import br.com.ffscompany.easyorder.repository.ClientRepository;
import br.com.ffscompany.easyorder.repository.OrderRepository;
import br.com.ffscompany.easyorder.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EasyOrderApplicationTests {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    final ClientDTO clientDTO = new ClientDTO(null, "Gabriel", "464.618.600-45", "(67) 91234-5678", "gabriel@email.com");

    final ProductDTO productDTO = new ProductDTO(null, "Produto", "kg", new BigDecimal("19.99"));

    OrderDTO orderDTO;
    @Test
    void crud() throws Exception {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();
        registerClient(clientDTO);
        registerProduct(productDTO);
        var client = getClient(clientRepository.findAll().get(0).getId());
        var product = getProduct(productRepository.findAll().get(0).getId());

        this.orderDTO = new OrderDTO(null, null, "Pedido", client, List.of(product), null);
        registerOrder(orderDTO);
        var order = getOrder(orderRepository.findAll().get(0).getId());

        deleteClient(client.id());
        deleteProduct(product.id());
        deleteOrder(order.id());

        var listClients = getClients();
        var listProducts = getProducts();
        var listOrders = getOrders();
        assertEquals(0, listClients.size());
        assertEquals(0, listProducts.size());
        assertEquals(0, listOrders.size());
    }

    private ClientDTO getClient(Long id) {
        return restTemplate.getForObject(getServiceUrl() + "/cliente/" + id, ClientDTO.class);
    }

    private ProductDTO getProduct(Long id) {
        return restTemplate.getForObject(getServiceUrl() + "/produto/" + id, ProductDTO.class);
    }

    private OrderDTO getOrder(Long id) {
        return restTemplate.getForObject(getServiceUrl() + "/pedido/" + id, OrderDTO.class);
    }

    private void deleteClient(Long id) {
        var responseCode = restTemplate.exchange(getServiceUrl() + "/cliente/remover/" + id, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), String.class).getStatusCode();
        assertEquals(HttpStatus.OK, responseCode);
    }

    private void deleteProduct(Long id) {
        var responseCode = restTemplate.exchange(getServiceUrl() + "/produto/remover/" + id, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), String.class).getStatusCode();
        assertEquals(HttpStatus.OK, responseCode);
    }

    private void deleteOrder(Long id) {
        var responseCode = restTemplate.exchange(getServiceUrl() + "/pedido/remover/" + id, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), String.class).getStatusCode();
        assertEquals(HttpStatus.OK, responseCode);
    }

    private void registerClient(final ClientDTO clientDTO) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpRequest = new HttpEntity<>(objectMapper.writeValueAsString(clientDTO), httpHeaders);
        var responseCode = restTemplate.postForEntity(getServiceUrl() + "/cliente/cadastro", httpRequest, ClientDTO.class).getStatusCode();
        assertEquals(HttpStatus.CREATED, responseCode);
    }

    private void registerProduct(final ProductDTO productDTO) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpRequest = new HttpEntity<>(objectMapper.writeValueAsString(productDTO), httpHeaders);
        var responseCode = restTemplate.postForEntity(getServiceUrl() + "/produto/cadastro", httpRequest, ProductDTO.class).getStatusCode();
        assertEquals(HttpStatus.CREATED, responseCode);
    }

    private void registerOrder(final OrderDTO orderDTO) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpRequest = new HttpEntity<>(objectMapper.writeValueAsString(orderDTO), httpHeaders);
        var responseCode = restTemplate.postForEntity(getServiceUrl() + "/pedido/registro", httpRequest, OrderDTO.class).getStatusCode();
        assertEquals(HttpStatus.CREATED, responseCode);
    }

    private List<ClientDTO> getClients() {
        return restTemplate.exchange(getServiceUrl() + "/cliente/todos", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), List.class).getBody();
    }

    private List<ProductDTO> getProducts() {
        return restTemplate.exchange(getServiceUrl() + "/produto/todos", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), List.class).getBody();
    }

    private List<OrderDTO> getOrders() {
        return restTemplate.exchange(getServiceUrl() + "/pedido/todos", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), List.class).getBody();
    }

    private String getServiceUrl() {
        return "http://localhost:" + port;
    }
}
