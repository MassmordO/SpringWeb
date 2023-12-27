package org.example.finalproj.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document("userAccount")
public class UserAccount implements Identifiable{
    @MongoId
    private String id;
    @NotBlank
    @Size(min = 1, max = 50, message = "Size must be between 1 and 50!")
    private String name;
    @NotBlank
    @Size(min = 1, max = 50, message = "Size must be between 1 and 50!")
    private String sername;
    @Size(min = 1, max = 50, message = "Size must be between 1 and 50!")
    private String patronymic;

    @DBRef(lazy = true)
    private UserM userM;
    @DBRef(lazy = true)
    private UserStatus userStatus;


    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
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

    public String getSername() {
        return sername;
    }

    public void setSername(String sername) {
        this.sername = sername;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    public UserM getUserM() {
        return userM;
    }

    public void setUserM(UserM userM) {
        this.userM = userM;
    }
}
