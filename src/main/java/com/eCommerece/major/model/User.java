package com.eCommerece.major.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @NotEmpty
    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @NotEmpty
    @Column(nullable = false,unique=true)
    @Email(message = "{errors.invalid_email}")
    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name="USER_ID",referencedColumnName = "ID")},
        inverseJoinColumns = {@JoinColumn(name="ROLE_ID",referencedColumnName = "ID")}
    )

    private List<Role> roles;

    public User(User user) {
        // this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User(){

    }
    
    

}
