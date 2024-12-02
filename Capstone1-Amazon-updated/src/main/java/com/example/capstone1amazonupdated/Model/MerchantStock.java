package com.example.capstone1amazonupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class MerchantStock {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Positive(message = "not valid product ID")
    @Column(columnDefinition = "int not null")
    private Integer productID;
    @Positive(message = "not valid marchant ID")
    @Column(columnDefinition = "int not null")
    private Integer marchantID;
    @Positive(message = "not valid stock")
    @Min(value = 10,message = "stock must be start by 10")
    @Column(columnDefinition = "int not null")
    private Integer stock;
}
