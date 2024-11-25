package org.example.service;

import org.example.entity.Product;
import org.example.model.*;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public GetProductResponse getProduct(GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        Product product = productRepository.findById(request.getId()).orElse(null);
        if (product != null) {
            response.setProduct(product);
        }
        return response;
    }

    public GetAllProductsResponse getAllProducts(GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        List<Product> products = productRepository.findAll();
        if (products != null) {
            response.setGetProducts(products);
        }
        return response;
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product = productRepository.save(product);

        CreateProductResponse response = new CreateProductResponse();
        response.setProduct(product);
        return response;
    }

    public UpdateProductResponse updateProduct(UpdateProductRequest request) {
        Product product = productRepository.findById(request.getId()).orElse(null);
        if (product != null) {
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            product = productRepository.save(product);
        }

        UpdateProductResponse response = new UpdateProductResponse();
        response.setProduct(product);
        return response;
    }

    public DeleteProductResponse deleteProduct(DeleteProductRequest request) {
        productRepository.deleteById(request.getId());
        DeleteProductResponse response = new DeleteProductResponse();
        response.setSuccess(true);
        return response;
    }
}
