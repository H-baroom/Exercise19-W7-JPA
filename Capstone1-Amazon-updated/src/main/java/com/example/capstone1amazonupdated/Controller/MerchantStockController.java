package com.example.capstone1amazonupdated.Controller;

import com.example.capstone1amazonupdated.ApiResponse.ApiResponse;
import com.example.capstone1amazonupdated.Model.MerchantStock;
import com.example.capstone1amazonupdated.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getMerchantStock() {
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.addMerchants(merchantStock) == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock added"));
        } else if (merchantStockService.addMerchants(merchantStock) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("not found Merchant"));
        } else if (merchantStockService.addMerchants(merchantStock) == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("not found product"));
        }
        return ResponseEntity.status(400).body("MerchantStock not added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable Integer id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.updateMerchantStock(id,merchantStock)){
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("MerchantStock not exit"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable Integer id) {
        if (merchantStockService.deleteMerchantStock(id)){
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("MerchantStock not exit"));
    }
    @PutMapping("/merchantAddMoreStock/{productID}/{marchantID}/{amountOfAdditionalStock}")
    public ResponseEntity merchantAddMoreStock(@PathVariable Integer productID,@PathVariable Integer marchantID,@PathVariable Integer amountOfAdditionalStock){
        if (merchantStockService.merchantAddMoreStock(productID,marchantID,amountOfAdditionalStock)){
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock added more stock"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("MerchantStock not exit"));
    }
    @GetMapping("/getMerchantTotalStockAndPrice/{merchantID}")
    public ResponseEntity getMerchantTotalStockAndPrice(@PathVariable Integer merchantID){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantTotalStockAndPrice(merchantID));
    }

    @GetMapping("/transferStockBetweenMerchants/{sourceMerchantID}/{destinationMerchantID}/{productID}/{quantity}")
    public ResponseEntity transferStockBetweenMerchants(@PathVariable Integer sourceMerchantID,@PathVariable Integer destinationMerchantID,@PathVariable Integer productID,@PathVariable Integer quantity){
        return ResponseEntity.status(200).body(merchantStockService.transferStockBetweenMerchants(sourceMerchantID,destinationMerchantID,productID,quantity));
    }

}
