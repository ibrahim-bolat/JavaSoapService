package org.example.client.controller;

import org.example.client.clientservice.ProductClient;
import org.example.serviceproxy.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductClient productClient;

    public ProductController(ProductClient productClient) {
        this.productClient = productClient;
    }

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
