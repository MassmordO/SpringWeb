package org.example.finalproj.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document("product")
public class Product implements Identifiable{
    @MongoId
    private String id;
    @NotBlank
    @Size(min = 1, max = 50, message = "Size must be between 1 and 50!")
    private String name;
    @NotBlank
    private double priceBuy;
    @NotBlank
    private double pricSell;
    @NotBlank
    @Max(value = 100)
    @Min(value = 0)
    private double discount;
    @NotBlank
    private String description;
    @NotBlank
    private boolean isExist;
    @DBRef(lazy = true)
    private ProductType productType;
    @DBRef(lazy = true)
    private List<Photo> photos;

    @Override
    public String toString() {
        return name+" "+id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(double priceBuy) {
        this.priceBuy = priceBuy;
    }

    public double getPricSell() {
        return pricSell;
    }

    public void setPricSell(double pricSell) {
        this.pricSell = pricSell;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Photo photo) {
        boolean exist=false;
        if(this.photos == null){
            ArrayList<Photo> bk = new ArrayList<>();
            bk.add(photo);
            this.photos=bk;
        }
        else {
            for (Photo book : this.photos) {
                if(book.getId().equals(photo.getId())) {
                    exist = true;
                }
            }
            if(!exist)
                this.photos.add(photo);
        }
    }
}
