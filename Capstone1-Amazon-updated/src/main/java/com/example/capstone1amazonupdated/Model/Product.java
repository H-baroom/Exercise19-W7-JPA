package com.example.capstone1amazonupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @NotEmpty(message = "not valid name")
    @Size(min = 4,message = "name length must be more then 3 character")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;
    @Positive(message = "price must be positive")
    @Column(columnDefinition = "int not null")
    private Integer price;
    @Positive(message = "not valid category ID")
    @Column(columnDefinition = "int not null ")
    private Integer categoryID;

}
