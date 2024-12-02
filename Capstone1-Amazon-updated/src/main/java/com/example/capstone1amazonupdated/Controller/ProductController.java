package com.example.capstone1amazonupdated.Controller;

import com.example.capstone1amazonupdated.ApiResponse.ApiResponse;
import com.example.capstone1amazonupdated.Model.Product;
import com.example.capstone1amazonupdated.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.addProduct(product)){
            return ResponseEntity.status(200).body(new ApiResponse("Product added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product category not found"));
    }
    @GetMapping("/get")
    public List<Product> getProduct(){
        return productService.getProduct();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.updateProduct(id, product)) {
            return ResponseEntity.status(200).body(new ApiResponse("product updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id){
        if (productService.deleteProduct(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }

}
