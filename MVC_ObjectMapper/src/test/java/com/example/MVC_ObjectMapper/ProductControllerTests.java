package com.example.MVC_ObjectMapper;

import com.example.MVC_ObjectMapper.Controllers.ProductController;
import com.example.MVC_ObjectMapper.Entities.Product;
import com.example.MVC_ObjectMapper.Exceptions.ProductWasNotFoundException;
import com.example.MVC_ObjectMapper.Repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductControllerTests {

    private ProductRepository productRepository;
    private ObjectMapper objectMapper;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        objectMapper = new ObjectMapper();
        productController = new ProductController(productRepository, objectMapper);
    }

    @Test
    void testGetAllProducts() throws JsonProcessingException {
        // Arrange
        Product product1 = new Product(1, "Lamp", "Nice lamp", 10, 100);
        Product product2 = new Product(2, "Table", "Wooden table", 50, 50);
        when(productRepository.findAll()).thenReturn(java.util.List.of(product1, product2));

        // Act
        String resultJson = productController.getAllProducts();

        // Assert
        assertThat(resultJson).isEqualTo("[{\"productId\":1,\"name\":\"Lamp\",\"description\":\"Nice lamp\",\"price\":10,\"quantityInStock\":100},{\"productId\":2,\"name\":\"Table\",\"description\":\"Wooden table\",\"price\":50,\"quantityInStock\":50}]");
    }

    @Test
    void testGetProduct_ProductExists() throws JsonProcessingException {
        // Arrange
        long productId = 1;
        Product product = new Product(productId, "Lamp", "Nice lamp", 10, 100);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        String resultJson = productController.getProduct(productId);

        // Assert
        assertThat(resultJson).isEqualTo("{\"productId\":1,\"name\":\"Lamp\",\"description\":\"Nice lamp\",\"price\":10,\"quantityInStock\":100}");
    }

    @Test
    void testGetProduct_ProductNotFound() {
        // Arrange
        long productId = 1;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> productController.getProduct(productId))
                .isInstanceOf(ProductWasNotFoundException.class)
                .hasMessage("product with this id does not exist");
    }

    @Test
    void testAddProduct() throws JsonProcessingException {
        // Arrange
        String productJson = "{\"productId\":1,\"name\":\"Lamp\",\"description\":\"Nice lamp\",\"price\":10,\"quantityInStock\":100}";
        Product product = new Product(1, "Lamp", "Nice lamp", 10, 100);
        when(productRepository.save(any())).thenReturn(product);

        // Act
        Product savedProduct = productController.addProduct(productJson);

        // Assert
        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    void testUpdateProduct_ProductExists() throws JsonProcessingException {
        // Arrange
        long productId = 1;
        String productJson = "{\"productId\":1,\"name\":\"Lamp\",\"description\":\"Nice lamp\",\"price\":10,\"quantityInStock\":100}";
        Product product = new Product(productId, "Lamp", "Nice lamp", 10, 100);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(any())).thenReturn(product);

        // Act
        Product modifiedProduct = productController.updateProduct(productJson, productId);

        // Assert
        assertThat(modifiedProduct).isEqualTo(product);
    }


    @Test
    void testDeleteProduct_ProductExists() {
        // Arrange
        long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        productController.deleteProduct(productId);

        // Assert: Verify that deleteById is called with the correct ID
        verify(productRepository).deleteById(productId);
    }
}
