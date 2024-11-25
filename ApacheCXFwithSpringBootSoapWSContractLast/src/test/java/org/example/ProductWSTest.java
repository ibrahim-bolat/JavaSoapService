package org.example;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import org.example.entity.Product;
import org.example.service.IProductService;

import java.net.URL;
import java.text.MessageFormat;


public class ProductWSTest {

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:8080/services/ProductService?wsdl");

        QName qname = new QName("http://service.example.org/", "ProductService");

        Service service = Service.create(url, qname);

        IProductService productService = service.getPort(IProductService.class);

        for (Product product : productService.getAllProducts()) {
            System.out.println(MessageFormat.format("Product soap service response {0} {1} {2}", product.getId(), product.getName(), product.getPrice()));
        }
    }
}