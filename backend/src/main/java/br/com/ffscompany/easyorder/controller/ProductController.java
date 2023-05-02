package br.com.ffscompany.easyorder.controller;

import br.com.ffscompany.easyorder.dto.ProductDTO;
import br.com.ffscompany.easyorder.model.ProductModel;
import br.com.ffscompany.easyorder.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("produto")
@Transactional
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Endpoint to register a new product in the system.
     *
     * @param product    object containing the information of the product to be registered.
     * @param uriBuilder object used to build the response URI.
     * @return ResponseEntity containing the registered product object and the access URI.
     */
    @PostMapping("cadastro")
    public ResponseEntity<ProductDTO> productRegister(@RequestBody @Valid ProductDTO product, UriComponentsBuilder uriBuilder) {
        ProductModel registeredProduct = productService.register(product);
        return getResponseEntity(uriBuilder, registeredProduct);
    }

    /**
     * Endpoint to edit an existing product in the system.
     *
     * @param product    object containing the information of the product to be edited.
     * @param uriBuilder object used to build the response URI.
     * @return ResponseEntity containing the edited product object and the access URI, or not found if the product does not exist.
     */
    @PutMapping("editar")
    public ResponseEntity editOrder(@RequestBody @Valid ProductDTO product, UriComponentsBuilder uriBuilder) {
        Optional<ProductModel> editedProduct = productService.edit(product);
        if (editedProduct.isPresent()) {
            return getResponseEntity(uriBuilder, editedProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Helper method to build the ResponseEntity containing the product object and the access URI.
     *
     * @param uriBuilder   object used to build the response URI.
     * @param productModel the product model object.
     * @return ResponseEntity containing the product object and the access URI.
     */
    private static ResponseEntity<ProductDTO> getResponseEntity(UriComponentsBuilder uriBuilder, ProductModel productModel) {
        return ResponseEntity
                .created(uriBuilder.path("/produto/{id}").buildAndExpand(productModel.getId()).toUri())
                .body(new ProductDTO(
                        productModel.getId(),
                        productModel.getDescription(),
                        productModel.getUnit(),
                        productModel.getPrice()));
    }

    /**
     * Endpoint to delete an existing product from the system by its ID.
     *
     * @param id the ID of the product to be deleted.
     * @return ResponseEntity with no content if the product is successfully deleted.
     */
    @DeleteMapping("remover/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        productService.remove(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to retrieve all products registered in the system.
     *
     * @return ResponseEntity containing a list of ProductDTO objects corresponding to the registered products.
     */
    @GetMapping("todos")
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        List<ProductDTO> productDTOS = productService.findAll().stream().map(productModel -> new ProductDTO(
                productModel.getId(),
                productModel.getDescription(),
                productModel.getUnit(),
                productModel.getPrice())).collect(Collectors.toList());
        return ResponseEntity.ok(productDTOS);
    }

    /**
     * Endpoint to retrieve a product registered in the system by its ID.
     *
     * @param id the ID of the product to be retrieved.
     * @return ResponseEntity containing the ProductDTO object corresponding to the retrieved product, or not found if the product does not exist.
     */
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        Optional<ProductModel> productModel = productService.findById(id);
        if (productModel.isPresent()) {
            ProductDTO productDTO = new ProductDTO(
                    productModel.get().getId(),
                    productModel.get().getDescription(),
                    productModel.get().getUnit(),
                    productModel.get().getPrice());
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }
}