package com.example.MVC_ObjectMapper.Controllers;

import com.example.MVC_ObjectMapper.Entities.Product;
import com.example.MVC_ObjectMapper.Exceptions.ProductWasNotFoundException;
import com.example.MVC_ObjectMapper.Repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {
    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/getProducts")
    public String getAllProducts() throws JsonProcessingException {
        return objectMapper.writeValueAsString(productRepository.findAll());
    }

    @GetMapping("/getProduct")
    public String getProduct(@PathVariable  Long id) throws JsonProcessingException {
        if (!productRepository.existsById(id)) {
            throw new ProductWasNotFoundException(
                    "product with this id does not exist");
        }
        return objectMapper.writeValueAsString(productRepository.findById(id).get());
    }

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody String product) throws JsonProcessingException {
        Product savedProduct = objectMapper.readValue(product,Product.class);
        return productRepository.save(savedProduct);
    }

    @PutMapping("/updateProduct/{id}")
    public Product updateProduct(@RequestBody String product, @PathVariable Long id) throws JsonProcessingException {
        if (!productRepository.existsById(id)) {
            throw new ProductWasNotFoundException(
                    "product with this id does not exist");
        }
        Product modifiedProduct = objectMapper.readValue(product,Product.class);
        return productRepository.save(modifiedProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductWasNotFoundException(
                    "product with this id does not exist");
        }
        productRepository.deleteById(id);
    }

    @ExceptionHandler(ProductWasNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductWasNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
