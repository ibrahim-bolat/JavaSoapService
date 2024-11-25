package org.example.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import org.example.entity.Product;

import java.util.List;


@WebService(name = "ProductService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IProductService {


    @WebMethod
    public Product getProduct(@WebParam(name = "id") Long id);

    @WebMethod
    public List<Product> getAllProducts();

    @WebMethod
    public Product createProduct(@WebParam(name = "product") Product product);

    @WebMethod
    public Product updateProduct(@WebParam(name = "product") Product product);

    @WebResult(name = "Product")
    public boolean deleteProduct(@WebParam(name = "id") Long id);
}