@jakarta.xml.bind.annotation.XmlSchema(namespace  = NAMESPACE_URI, elementFormDefault = jakarta.xml.bind.annotation.XmlNsForm.QUALIFIED)
package org.example.model;

import static org.example.constant.WSEndpoint.NAMESPACE_URI;
//entity ve model sınıflarını contract-last yaklaşımına göre kendim manuel oluşturarak
// entity ve model paketlerinin içine bu dosyadaki gibi paket bilgisini ekliyoruzki java sınıflarından
// xsd oluşturan jaxb2-maven-plugin'i namespace bilgini vb düzgün yaparak servisin wsdl i hatasız oluşturmasını sağlasın
// package-info.java sınıfına bunları yazmadan xsd düzgün oluşmuyor