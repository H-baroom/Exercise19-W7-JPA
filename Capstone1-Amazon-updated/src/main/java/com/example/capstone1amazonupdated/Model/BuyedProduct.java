package com.example.capstone1amazonupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class BuyedProduct {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Positive( message = "not valid id")
    @Column(columnDefinition = "int not null")
    private Integer userID;
    @NotEmpty(message = "not valid userName")
    @Size(min = 6,message = "userName length must be more then 5 character")
    @Column(columnDefinition = "varchar(30) not null")
    private String userName;
    @NotEmpty(message ="password must not be empty")
    @Size(min = 7, message = "Password must be at least 7 characters long.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]*$",
            message = "Password must contain both letters and digits."
    )
    @Column(columnDefinition = "varchar(30) not null")
    private String password;
    @NotEmpty(message = "email must not be empty")
    @Email(message = "not correct syntax of email")
    @Column(columnDefinition = "varchar(30) not null")
    private String email;
    @NotEmpty(message = "role must not be empty")
    @Pattern(regexp = "^(Admin|Customer)$",message = "must be Admin or Customer")
    @Column(columnDefinition = "varchar(30) not null")
    private String role;
    @NotNull(message = "balance must not be empty")
    @Positive(message = "balance must be positive")
    @Column(columnDefinition = "int not null")
    private Integer balance;
    @Column(columnDefinition = "date not null")
    private LocalDate registrationDate;
    @Column(columnDefinition = "int ")
    private Integer points=0;



    @Positive( message = "not valid id")
    @Column(columnDefinition = "int not null")
    private Integer productID;
    @NotEmpty(message = "not valid name")
    @Size(min = 4,message = "name length must be more then 3 character")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;
    @NotNull(message = "not valid price")
    @Positive(message = "price must be positive")
    @Column(columnDefinition = "int not null")
    private Integer price;
    @NotNull(message = "not valid category ID")
    @Column(columnDefinition = "int not null ")
    private Integer categoryID;
}
