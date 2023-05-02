package br.com.ffscompany.easyorder.service;

import br.com.ffscompany.easyorder.dto.ProductDTO;
import br.com.ffscompany.easyorder.model.ProductModel;
import br.com.ffscompany.easyorder.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements br.com.ffscompany.easyorder.service.Service<ProductModel, ProductDTO> {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Registers a new product in the system.
     *
     * @param productToRegister the product to be registered.
     * @return the registered product.
     */
    public ProductModel register(ProductDTO productToRegister) {
        ProductModel productModel = new ProductModel(
                null,
                productToRegister.description(),
                productToRegister.unit(),
                productToRegister.price()
        );
        productRepository.save(productModel);

        return productModel;
    }

    /**
     * Edits an existing product in the system.
     *
     * @param productToEdit the product to be edited.
     * @return an optional containing the edited product, or an empty optional if the product was not found.
     */
    @Override
    public Optional<ProductModel> edit(ProductDTO productToEdit) {

        Optional<ProductModel> productModel = productRepository.findById(productToEdit.id());

        productModel.ifPresent(product -> {
                    product.setDescription(productToEdit.description());
                    product.setUnit(productToEdit.unit());
                    product.setPrice(productToEdit.price());
                    productRepository.save(product);
                }
        );
        return productModel;
    }

    /**
     * Removes a product from the system.
     *
     * @param id the id of the product to be removed.
     */
    @Override
    public void remove(Long id) {
        Optional<ProductModel> productModel = productRepository.findById(id);
        productModel.ifPresent(product -> productRepository.delete(product));
    }

    /**
     * Retrieves a list of all products in the system.
     *
     * @return a list of all products in the system.
     */
    @Override
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its id.
     *
     * @param id the id of the product to be retrieved.
     * @return an optional containing the retrieved product, or an empty optional if the product was not found.
     */
    @Override
    public Optional<ProductModel> findById(Long id) {
        return productRepository.findById(id);
    }
}
