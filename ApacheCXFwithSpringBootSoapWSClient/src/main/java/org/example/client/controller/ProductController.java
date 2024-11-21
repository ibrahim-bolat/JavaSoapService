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

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) {
        return productServiceClient.addProduct(product);
    }

    @GetMapping("/getProduct/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productServiceClient.getProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productServiceClient.getAllProducts();
    }

    @PutMapping("/updateProduct")
    public Product updateProduct(@RequestBody Product product) {
        return productServiceClient.updateProduct(product);
    }

    @DeleteMapping("deleteProduct/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return productServiceClient.deleteProduct(id);
    }
}