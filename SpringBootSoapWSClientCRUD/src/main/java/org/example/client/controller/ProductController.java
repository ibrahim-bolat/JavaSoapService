package org.example.client.controller;

import org.example.client.clientservice.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.serviceproxy.Product;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductClient productClient;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productClient.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productClient.getProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productClient.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return productClient.deleteProduct(id);
    }
}
