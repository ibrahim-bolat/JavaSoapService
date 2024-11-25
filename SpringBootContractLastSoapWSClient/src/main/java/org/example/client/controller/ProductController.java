package org.example.client.controller;

import org.example.client.clientservice.ProductClient;
import org.example.serviceproxy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/{id}")
    public GetProductResponse getProduct(@PathVariable Long id) {
        return productClient.getProduct(id);
    }

    @GetMapping
    public GetAllProductsResponse getAllProducts() {
        return productClient.getAllProducts();
    }

    @PostMapping
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productClient.createProduct(request);
    }

    @PutMapping("/{id}")
    public UpdateProductResponse updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        request.setId(id);
        return productClient.updateProduct(request);
    }

    @DeleteMapping("/{id}")
    public DeleteProductResponse deleteProduct(@PathVariable Long id) {
        return productClient.deleteProduct(id);
    }
}
