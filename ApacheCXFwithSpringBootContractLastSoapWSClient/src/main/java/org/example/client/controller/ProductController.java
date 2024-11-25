package org.example.client.controller;

import org.example.client.clientservice.ProductServiceClient;
import org.example.serviceproxy.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceClient productServiceClient;

    public ProductController(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productServiceClient.getProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productServiceClient.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productServiceClient.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productServiceClient.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return productServiceClient.deleteProduct(id);
    }
}