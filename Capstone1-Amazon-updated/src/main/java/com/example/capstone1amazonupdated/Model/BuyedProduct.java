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
    @Positive( message = "not valid id")
    @Column(columnDefinition = "int not null")
    private Integer productID;
    @Positive(message = "not valid category ID")
    @Column(columnDefinition = "int not null ")
    private Integer categoryID;

}
