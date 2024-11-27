package org.example.client.clientservice;


import jakarta.xml.bind.JAXBElement;
import org.example.serviceproxy.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import static org.example.client.constant.WSEndpoint.WS_ENDPOINT_URL;


public class ProductClient extends WebServiceGatewaySupport {

    private ObjectFactory objectFactory;

    public ProductClient() {
        objectFactory = new ObjectFactory();
    }

    public GetProductResponse getProduct(Long id) {
        GetProductRequest request = new GetProductRequest();
        request.setId(id);
        //wsdlden oluşturulan pojolarda @XmlRootElement ve @XmlElement anatasyonları olmayınca hata veriyor o nedenle ObjectFactory nesnesi ile
        //JAXBElement olarak sarmalanarak istek oluşturuluyorki hata vermesin
        JAXBElement<GetProductRequest> getProductRequest = objectFactory.createGetProductRequest(request);
        //Request SOAP_SERVICE_URL ile birlikte endpoint teki isteği karşılayacak metodun isminin birleşiminden oluşur.
        JAXBElement<GetProductResponse> response = (JAXBElement<GetProductResponse>)getWebServiceTemplate().marshalSendAndReceive(WS_ENDPOINT_URL + "/getProduct", getProductRequest);
        return response.getValue();
    }

    public GetAllProductsResponse getAllProducts() {
        GetAllProductsRequest request = new GetAllProductsRequest();
        //wsdlden oluşturulan pojolarda @XmlRootElement ve @XmlElement anatasyonları olmayınca hata veriyor o nedenle ObjectFactory nesnesi ile
        //JAXBElement olarak sarmalanarak istek oluşturuluyorki hata vermesin
        JAXBElement<GetAllProductsRequest> getAllProductsRequest = objectFactory.createGetAllProductsRequest(request);
        //Request SOAP_SERVICE_URL ile birlikte endpoint teki isteği karşılayacak metodun isminin birleşiminden oluşur.
        JAXBElement<GetAllProductsResponse> response= (JAXBElement<GetAllProductsResponse>)getWebServiceTemplate().marshalSendAndReceive(WS_ENDPOINT_URL + "/getAllProducts", getAllProductsRequest);
        return response.getValue();
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {
        //wsdlden oluşturulan pojolarda @XmlRootElement ve @XmlElement anatasyonları olmayınca hata veriyor o nedenle ObjectFactory nesnesi ile
        //JAXBElement olarak sarmalanarak istek oluşturuluyorki hata vermesin
        JAXBElement<CreateProductRequest> createProductRequest = objectFactory.createCreateProductRequest(request);
        //Request SOAP_SERVICE_URL ile birlikte endpoint teki isteği karşılayacak metodun isminin birleşiminden oluşur.
        JAXBElement<CreateProductResponse> response = (JAXBElement<CreateProductResponse>)getWebServiceTemplate().marshalSendAndReceive(WS_ENDPOINT_URL + "/createProduct", createProductRequest);
        return response.getValue();
    }

    public UpdateProductResponse updateProduct(UpdateProductRequest request) {
        //wsdlden oluşturulan pojolarda @XmlRootElement ve @XmlElement anatasyonları olmayınca hata veriyor o nedenle ObjectFactory nesnesi ile
        //JAXBElement olarak sarmalanarak istek oluşturuluyorki hata vermesin
        JAXBElement<UpdateProductRequest> updateProductRequest = objectFactory.createUpdateProductRequest(request);
        //Request SOAP_SERVICE_URL ile birlikte endpoint teki isteği karşılayacak metodun isminin birleşiminden oluşur.
        JAXBElement<UpdateProductResponse> response = (JAXBElement<UpdateProductResponse>)getWebServiceTemplate().marshalSendAndReceive(WS_ENDPOINT_URL + "/updateProduct", updateProductRequest);
        return response.getValue();
    }

    public DeleteProductResponse deleteProduct(Long id) {
        DeleteProductRequest request = new DeleteProductRequest();
        request.setId(id);
        //wsdlden oluşturulan pojolarda @XmlRootElement ve @XmlElement anatasyonları olmayınca hata veriyor o nedenle ObjectFactory nesnesi ile
        //JAXBElement olarak sarmalanarak istek oluşturuluyorki hata vermesin
        JAXBElement<DeleteProductRequest> deleteProductRequest = objectFactory.createDeleteProductRequest(request);
        //Request SOAP_SERVICE_URL ile birlikte endpoint teki isteği karşılayacak metodun isminin birleşiminden oluşur.
        JAXBElement<DeleteProductResponse> response= ((JAXBElement<DeleteProductResponse>)getWebServiceTemplate().marshalSendAndReceive(WS_ENDPOINT_URL + "/deleteProduct", deleteProductRequest));
        return response.getValue();
    }
}
