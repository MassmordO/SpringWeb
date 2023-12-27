package org.example.finalproj.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document("user")
public class UserM implements Identifiable{
    @MongoId
    private String id;
    @NotBlank
    @Size(min = 1, max = 100, message = "Size must be between 1 and 100!")
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
    @Indexed(unique = true)
    private String email;
    @NotBlank
    @Size(min = 8, max = 100,message = "Size must be between 1 and 100!")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)")
    private String password;
    @NotBlank
    private boolean IsExist;
    @DBRef(lazy = true)
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(Role role) {
        boolean exist=false;
        if(this.roles == null){
            ArrayList<Role> bk = new ArrayList<>();
            bk.add(role);
            this.roles=bk;
        }
        else {
            for (Role book : this.roles) {
                if(book.getId().equals(role.getId())) {
                    exist = true;
                }
            }
            if(!exist)
                this.roles.add(role);
        }
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExist() {
        return IsExist;
    }

    public void setExist(boolean exist) {
        IsExist = exist;
    }
}
