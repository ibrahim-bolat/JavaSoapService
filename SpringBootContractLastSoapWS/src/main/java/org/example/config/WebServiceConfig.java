package org.example.config;

import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Collections;
import java.util.List;

import static org.example.constant.WSEndpoint.NAMESPACE_URI;


@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Value("${ws_server_username}")
    private String serverUsername;

    @Value("${ws_server_password}")
    private String serverPassword;

    @Value("${ws_client_username}")
    private String clientUsername;

    @Value("${ws_client_password}")
    private String clientPassword;

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "products")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema productsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setServiceName("ProductsService");
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(NAMESPACE_URI);
        wsdl11Definition.setSchema(productsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema productsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsds/products.xsd"));
    }

    //Dönen response header a server security bilgileri eklenerek doğrulama yapılıyor.
    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);//response header a username token ekleniyor
        securityInterceptor.setSecurementPasswordType(WSConstants.PW_DIGEST); //response header daki password type belirleniyor
        securityInterceptor.setSecurementUsername(serverUsername); //response header a server username ekleniyor
        securityInterceptor.setSecurementPassword(serverPassword); //response header a server password ekleniyor
        securityInterceptor.setValidationActions(WSHandlerConstants.USERNAME_TOKEN);//request header daki client username token doğrulaması yapılıyor.Bu satır çok önemli bu olmadan alttaki callbackHandler ile doğrulama yapılmaz.
        securityInterceptor.setValidationCallbackHandler(callbackHandler()); //request header daki client username ve password doğrulaması yapılıyor

        return securityInterceptor;
    }

    //Client tarafından gelen request teki client username ve password doğrulanıyor
    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        handler.setUsersMap(Collections.singletonMap(clientUsername, clientPassword));
        return handler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }
}