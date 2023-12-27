package org.example.finalproj.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document("cart")
public class Cart implements Identifiable {
    @MongoId
    private String id;
    @DBRef(lazy = true)
    private UserM user;
    @DBRef(lazy = true)
    private List<Product> productList;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public UserM getUser() {
        return user;
    }

    public void setUser(UserM user) {
        this.user = user;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
