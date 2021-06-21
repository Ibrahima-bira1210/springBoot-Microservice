package com.springboot.micrioservice.productservice.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findProductById(String id);
}
