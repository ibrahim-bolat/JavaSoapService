package org.example.client.config;

import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.example.client.clientservice.ProductClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;

import java.util.Collections;

@Configuration
public class ProductClientConfig {

    @Value("${ws_username}")
    private String username;

    @Value("${ws_password}")
    private String password;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.example.serviceproxy");
        return marshaller;
    }

    @Bean
    public ProductClient productClient() {
        ProductClient client = new ProductClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setWebServiceTemplate(webServiceTemplate());
        return client;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);
        securityInterceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
        securityInterceptor.setSecurementUsername(username);
        securityInterceptor.setSecurementPassword(password);

        // Giden istekler için güvenlik doğrulama
        securityInterceptor.setValidationCallbackHandler(callbackHandler());

        return securityInterceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        handler.setUsersMap(Collections.singletonMap(username, password));
        return handler;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setInterceptors(new ClientInterceptor[] { securityInterceptor() });
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        return webServiceTemplate;
    }
}