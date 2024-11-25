package org.example.endpoint;


import jakarta.xml.bind.JAXBElement;
import org.example.model.*;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static org.example.constant.WSEndpoint.NAMESPACE_URI;

@Endpoint
public class ProductEndpoint {

    private final ProductService productService;
    private final ObjectFactory objectFactory;

    @Autowired
    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
        this.objectFactory = new ObjectFactory();;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public JAXBElement<GetProductResponse> getProduct(@RequestPayload GetProductRequest request) {
        return objectFactory.createGetProductResponse(productService.getProduct(request));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public JAXBElement<GetAllProductsResponse> getAllProducts(@RequestPayload GetAllProductsRequest request) {
        return objectFactory.createGetAllProductsResponse(productService.getAllProducts(request));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createProductRequest")
    @ResponsePayload
    public JAXBElement<CreateProductResponse> createProduct(@RequestPayload CreateProductRequest request) {
        return objectFactory.createCreateProductResponse(productService.createProduct(request));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateProductRequest")
    @ResponsePayload
    public JAXBElement<UpdateProductResponse> updateProduct(@RequestPayload UpdateProductRequest request) {
        return objectFactory.createUpdateProductResponse(productService.updateProduct(request));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteProductRequest")
    @ResponsePayload
    public JAXBElement<DeleteProductResponse> deleteProduct(@RequestPayload DeleteProductRequest request) {
        return objectFactory.createDeleteProductResponse(productService.deleteProduct(request));
    }
}