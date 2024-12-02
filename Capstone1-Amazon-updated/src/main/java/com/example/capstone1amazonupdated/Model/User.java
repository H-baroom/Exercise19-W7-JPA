package com.example.capstone1amazonupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
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
    @Positive(message = "balance must be positive")
    @Column(columnDefinition = "int not null")
    private Integer balance;
    //private ArrayList<Product> buyedProducts = new ArrayList<>();
    @Column(columnDefinition = "date not null")
    private LocalDate registrationDate;
    @Column(columnDefinition = "int")
    private Integer points =0;


    @PrePersist
    public void prePersist() {
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
    }
}
