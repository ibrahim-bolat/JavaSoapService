package org.example.config;


import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.example.security.ServerPasswordCallback;
import org.example.service.IProductService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CxfConfig {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public ServletRegistrationBean<CXFServlet> cxfServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/services/*");
    }

    @Bean
    public Endpoint endpoint(IProductService productService) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), productService);
        endpoint.publish("/ProductService");


        // WS-Security İnterceptörleri
        // Gelen istekler için doğrulama yapılandırması ekliyoruz (ServerPasswordCallback sınıfında doğrulanacak)
        Map<String, Object> inProps = new HashMap<>();
        inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());

        // Gelen istekler için güvenlik interceptörü ekleniyor
        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
        endpoint.getInInterceptors().add(wssIn);

        return  endpoint;
    }
}