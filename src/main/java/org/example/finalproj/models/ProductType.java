package org.example.finalproj.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document("productType")
public class ProductType implements Identifiable{
    @MongoId
    private String id;
    @Size(min = 1, max = 50, message = "Size must be between 1 and 50!")
    @NotBlank
    @Indexed(unique = true)
    private String name;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }
}
