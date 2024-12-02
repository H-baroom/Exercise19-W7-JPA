package com.example.capstone1amazonupdated.Service;

import com.example.capstone1amazonupdated.Model.Category;
import com.example.capstone1amazonupdated.Model.Merchant;
import com.example.capstone1amazonupdated.Model.MerchantStock;
import com.example.capstone1amazonupdated.Model.Product;
import com.example.capstone1amazonupdated.Repository.CategoryRepository;
import com.example.capstone1amazonupdated.Repository.MerchantRepository;
import com.example.capstone1amazonupdated.Repository.MerchantStockRepository;
import com.example.capstone1amazonupdated.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    //ArrayList<MerchantStock> merchantStocks = new ArrayList();
    private final MerchantStockRepository merchantStockRepository;
    //private final ProductService productService;
    private final ProductRepository productRepository;
    //private final MerchantService merchantService;


    private final MerchantRepository merchantRepository;



    ////////////////////////////
    public Integer addMerchants(MerchantStock merchantStock) {
        List<Merchant> allmerchant = merchantRepository.findAll();
        List<Product> allproducts = productRepository.findAll();
        for (int i = 0; i < allproducts.size(); i++) {
            if (allproducts.get(i).getId() == merchantStock.getProductID()) {
                for (int j = 0; j < allmerchant.size(); j++) {
                    if (allmerchant.get(j).getId() == merchantStock.getMarchantID()) {
                        merchantStockRepository.save(merchantStock);
                        return 1;
                    }
                }return 2;
            }
        }
        return 3;
    }
    public List<MerchantStock> getMerchantStocks() {
        return merchantStockRepository.findAll();
    }

    public Boolean updateMerchantStock(Integer id,MerchantStock merchantStock) {
        List<Product> allproducts = productRepository.findAll();
        List<MerchantStock> allmerchantStock = merchantStockRepository.findAll();
        for (int i = 0; i < allproducts.size(); i++) {
            if (allproducts.get(i).getId() == merchantStock.getProductID()) {
                for (int j = 0; j < allmerchantStock.size(); j++) {
                    if (allmerchantStock.get(j).getId() == id) {
                        allmerchantStock.get(j).setStock(merchantStock.getStock());
                        allmerchantStock.get(j).setMarchantID(merchantStock.getMarchantID());
                        allmerchantStock.get(j).setProductID(merchantStock.getProductID());
                        merchantStockRepository.save(allmerchantStock.get(j));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean deleteMerchantStock(Integer id) {
        List<MerchantStock> allmerchantStock2 = merchantStockRepository.findAll();
        for(MerchantStock ms : allmerchantStock2){
            if(id == ms.getId()){
                merchantStockRepository.delete(ms);
                return true;
            }
        }
        return false;
    }

    public Boolean merchantAddMoreStock(Integer productID, Integer marchantID,Integer amountOfAdditionalStock) {
        List<MerchantStock> allmerchantStock = merchantStockRepository.findAll();
        for (int i = 0; i < allmerchantStock.size(); i++) {
            if (allmerchantStock.get(i).getProductID() == productID) {
                if (allmerchantStock.get(i).getMarchantID() == marchantID ) {
                    allmerchantStock.get(i).setStock(allmerchantStock.get(i).getStock()+amountOfAdditionalStock);
                    merchantStockRepository.save(allmerchantStock.get(i));
                    return true;
                }
            }
        }
        return false;
    }

    //Third extra endpoint
    public String getMerchantTotalStockAndPrice(Integer merchantID) {
        int totalPrice=0;
        int totalstock=0;
        List<MerchantStock> allmerchantStock = merchantStockRepository.findAll();
        List<Product> allproducts = productRepository.findAll();
        for (int i = 0; i < allmerchantStock.size(); i++) {
            if (allmerchantStock.get(i).getMarchantID() == merchantID) {
                for (int j = 0; j < allproducts.size(); j++) {
                    if (allproducts.get(j).getId() == allmerchantStock.get(i).getProductID() ) {
                        totalPrice+=allproducts.get(j).getPrice() * allmerchantStock.get(i).getStock();
                        totalstock+=allmerchantStock.get(i).getStock();
                    }
                }
            }
        }
        if (totalPrice != 0 && totalstock != 0) {
            return "The stock value for this merchant ID :" + merchantID + " is " + totalPrice + ", which includes " + totalstock + " items";
        }else {
            return "This merchant ID :" + merchantID + " does not have any products in stock right now or not exit";
        }

    }


    public String transferStockBetweenMerchants(Integer sourceMerchantID, Integer destinationMerchantID, Integer productID, Integer quantity) {
        Integer sourceStock=0;
        Boolean sourceFound = false;
        Boolean destinationFound = false;

        List<MerchantStock> allmerchantStock = merchantStockRepository.findAll();
        for (int i = 0; i < allmerchantStock.size(); i++) {
            if (allmerchantStock.get(i).getMarchantID() == sourceMerchantID ) {
                if (allmerchantStock.get(i).getProductID().equals(productID)) {
                    sourceFound =true;
                    sourceStock+=allmerchantStock.get(i).getStock();
                }
            }
        }
        if (!sourceFound) {
            return "The product does not exist in the source merchant stock";
        }
        if (sourceStock >= quantity) {
            for (int i = 0; i < allmerchantStock.size(); i++) {
                if (allmerchantStock.get(i).getMarchantID() == destinationMerchantID ) {
                    if (allmerchantStock.get(i).getProductID() == productID ) {
                        destinationFound =true;
                        allmerchantStock.get(i).setStock(allmerchantStock.get(i).getStock()+quantity);
                        merchantStockRepository.save(allmerchantStock.get(i));
                        for (int j = 0; j < allmerchantStock.size(); j++) {
                            if (allmerchantStock.get(j).getMarchantID() == sourceMerchantID ) {
                                if (allmerchantStock.get(j).getProductID() == productID ) {
                                    allmerchantStock.get(j).setStock(allmerchantStock.get(j).getStock()-quantity);
                                    merchantStockRepository.save(allmerchantStock.get(j));
                                    return "The stock transfer has been completed successfully";
                                }
                            }
                        }
                    }
                }
            }
        }else {
            return "The quantity you requested is not available";
        }
        if (!destinationFound) {
            return "The product does not exist in the destination merchant stock";
        }
        return "Invalid request.";
    }


    }
