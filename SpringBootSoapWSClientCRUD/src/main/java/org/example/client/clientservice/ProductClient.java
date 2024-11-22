package org.example.client.clientservice;


import jakarta.annotation.PostConstruct;
import org.example.serviceproxy.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class ProductClient extends WebServiceGatewaySupport {

    private static final String SOAP_SERVICE_URL = "http://localhost:8080/ws";  // SOAP servisi URL'si

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate webServiceTemplate;

    @PostConstruct
    public void init() {
        this.webServiceTemplate = new WebServiceTemplate(marshaller);
    }

    public Product createProduct(Product product) {
        return (Product) webServiceTemplate.marshalSendAndReceive(SOAP_SERVICE_URL + "/createProduct", product);
    }

    public Product getProduct(Long id) {
        return (Product) webServiceTemplate.marshalSendAndReceive(SOAP_SERVICE_URL + "/getProduct", id);
    }

    public Product updateProduct(Product product) {
        return (Product) webServiceTemplate.marshalSendAndReceive(SOAP_SERVICE_URL + "/updateProduct", product);
    }

    public boolean deleteProduct(Long id) {
        return (Boolean) webServiceTemplate.marshalSendAndReceive(SOAP_SERVICE_URL + "/deleteProduct", id);
    }
}
