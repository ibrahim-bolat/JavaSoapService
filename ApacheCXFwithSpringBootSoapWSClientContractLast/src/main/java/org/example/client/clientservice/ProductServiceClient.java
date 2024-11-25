package org.example.client.clientservice;

import org.example.serviceproxy.Product;
import org.example.serviceproxy.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceClient {

    @Autowired
    private  ProductService productService;

    public Product getProduct(Long id) {
        return productService.getProduct(id);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    public Product createProduct(Product product) {
        return productService.createProduct(product);
    }

    public Product updateProduct(Product product) {
        return productService.updateProduct(product);
    }

    public boolean deleteProduct(Long id) {
        return productService.deleteProduct(id);
    }
}