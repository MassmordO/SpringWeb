package org.example.finalproj.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document("userStatus")
public class UserStatus implements Identifiable{
    @MongoId
    private String id;
    @NotBlank
    @Size(min = 1, max = 50, message = "Size must be between 1 and 50!")
    private String name;
    @NotBlank
    @Max(value = 100)
    @Min(value = 0)
    private double discount;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
