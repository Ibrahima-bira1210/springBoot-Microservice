package com.springboot.micrioservice.productservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public void addNewProduct(Product product) {
        Optional<Product> productOptional = productRepository
                .findProductById(product.getId());
        if(productOptional.isPresent()){
            throw new IllegalStateException("product exist");
        }
        productRepository.save(product);
    }

    public void deleteProduct(String productId) {
        boolean exit = productRepository.existsById(productId);
        if(!exit){
            throw new IllegalStateException("product with id "+ productId + " does not exists");
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public void updateProduct(String productId, String name, String description, BigDecimal price) {
        Product product = productRepository.findProductById(productId)
                .orElseThrow(()-> new IllegalStateException("product with id " + productId + " does no exist"));
        if (name != null &&
         name.length() > 0 && !Objects.equals(product.getName(),name)){
            product.setName(name);
        }
        if (description != null &&
                description.length() > 0 && !Objects.equals(product.getDescription(),description)){
            product.setDescription(description);
        }
        if (price != null &&
                price.compareTo(BigDecimal.valueOf(0)) > 0  && !Objects.equals(product.getPrice(),price)){
            product.setPrice(price);
        }
    }
}
