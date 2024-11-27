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

import static org.example.client.constant.WSEndpoint.WS_ENDPOINT_URL;
import static org.example.client.constant.WSEndpoint.MarshallerContextPath;

@Configuration
public class ProductClientConfig {

    @Value("${ws_client_username}")
    private String clientUsername;

    @Value("${ws_client_password}")
    private String clientPassword;

    @Value("${ws_server_username}")
    private String serverUsername;

    @Value("${ws_server_password}")
    private String serverPassword;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(MarshallerContextPath);
        return marshaller;
    }

    @Bean
    public ProductClient productClient() {
        ProductClient client = new ProductClient();
        client.setDefaultUri(WS_ENDPOINT_URL);
        client.setWebServiceTemplate(webServiceTemplate(marshaller()));
        return client;
    }

    // Giden istekler için header a client security bilgileri eklenerek doğrulama yapılıyor.
    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);//request header a username token ekleniyor
        securityInterceptor.setSecurementPasswordType(WSConstants.PW_DIGEST); //request header daki password type belirleniyor
        securityInterceptor.setSecurementUsername(clientUsername); //request header a client username ekleniyor
        securityInterceptor.setSecurementPassword(clientPassword); //request header a client password ekleniyor
        securityInterceptor.setValidationActions(WSHandlerConstants.USERNAME_TOKEN);//response header daki server username token doğrulaması yapılıyor. Bu satır çok önemli bu olmadan alttaki callbackHandler ile doğrulama yapılmaz.
        securityInterceptor.setValidationCallbackHandler(callbackHandler()); //response header daki server username ve password doğrulaması yapılıyor

        return securityInterceptor;
    }

    //Server tarafından gelen response daki server username ve password doğrulanıyor
    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        handler.setUsersMap(Collections.singletonMap(serverUsername, serverPassword));
        return handler;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] { securityInterceptor() });
        return webServiceTemplate;
    }
}