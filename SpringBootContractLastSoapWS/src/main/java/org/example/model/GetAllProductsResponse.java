
package org.example.model;

import jakarta.xml.bind.annotation.*;
import org.example.entity.Product;

import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllProductsResponse {

    @XmlElement
    protected List<Product> getProducts;

    public List<Product> getGetProducts() {
        return getProducts;
    }

    public void setGetProducts(List<Product> getProducts) {
        this.getProducts = getProducts;
    }
}
