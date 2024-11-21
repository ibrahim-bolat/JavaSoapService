package org.example.client.config;


import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.example.client.security.ClientPasswordCallback;
import org.example.serviceproxy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProductServiceClientConfig {

    @Bean
    public ProductService productService() {
        // WSDL üzerinden proxy sınıfını oluşturuyoruz
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(ProductService.class); // WSDL'den oluşturduğumuz proxy sınıfı
        factoryBean.setAddress("http://localhost:8080/services/ProductService"); // Servisin URL'si

        // Proxy sınıfı
        ProductService productService = (ProductService) factoryBean.create();

        // WS-Security İnterceptörleri
        // Giden istekler için doğrulama yapılandırması ekliyoruz (ClientPasswordCallback sınıfında doğrulanacak)
        Map<String, Object> outProps = new HashMap<>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        outProps.put(WSHandlerConstants.USER, "ibo");
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName()); // Callback sınıfı


        Client client =  ClientProxy.getClient(productService);
        Endpoint cxfEndpoint = client.getEndpoint();

        // Giden istekler için güvenlik interceptörü ekleniyor
        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
        cxfEndpoint.getOutInterceptors().add(wssOut);
        return productService;
    }
}
