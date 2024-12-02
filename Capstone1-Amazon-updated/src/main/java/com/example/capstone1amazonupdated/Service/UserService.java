package com.example.capstone1amazonupdated.Service;

import com.example.capstone1amazonupdated.Model.*;
import com.example.capstone1amazonupdated.Model.BuyedProduct;
import com.example.capstone1amazonupdated.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    //ArrayList<User> users = new ArrayList();
    private final UserRepository userRepository;
    //private final MerchantStockService merchantStockService;
    private final MerchantStockRepository merchantStockRepository;

    //private final ProductService productService;
    private final ProductRepository productRepository;

    //private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    private final RecommendationsRepository recommendationsRepository;
    private final BuyedProductRepository buyedProductRepository;

    public void addUser(User user){
        userRepository.save(user);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public Boolean updateUser(Integer id,LocalDate uodateDateToTest ,User user){
        List<User> allUsers = userRepository.findAll();
        BuyedProduct buyedProduct = new BuyedProduct();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getId() == id ) {
                allUsers.get(i).setUserName(user.getUserName());
                allUsers.get(i).setEmail(user.getEmail());
                allUsers.get(i).setPassword(user.getPassword());
                allUsers.get(i).setRole(user.getRole());
                allUsers.get(i).setRegistrationDate(uodateDateToTest);
                allUsers.get(i).setBalance(user.getBalance());
                buyedProduct.setUserID(user.getId());
                buyedProductRepository.save(buyedProduct);
                allUsers.get(i).setPoints(user.getPoints());
                userRepository.save(allUsers.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean deleteUser(Integer id){
        List<User> allUsers = userRepository.findAll();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getId() == id ) {
                userRepository.delete(allUsers.get(i));
                return true;
            }
        }
        return false;
    }
    public Integer  buyProductDirectly(Integer userID,Integer productID, Integer merchantID){
        Integer price =0;
        List<Product> allProducts = productRepository.findAll();
        List<MerchantStock> allMerchantStocks = merchantStockRepository.findAll();
        List<User> allUser = userRepository.findAll();
        BuyedProduct buyedProduct = new BuyedProduct();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productID){
                price = allProducts.get(i).getPrice();
            }
        }
        for (int i = 0; i < allMerchantStocks.size(); i++) {
            if (allMerchantStocks.get(i).getProductID() == productID) {
                if (allMerchantStocks.get(i).getMarchantID() == merchantID) {
                    if (allMerchantStocks.get(i).getStock()>=1) {
                        for (int j = 0; j < allUser.size(); j++) {
                            if (allUser.get(j).getId() == userID ) {
                                if (allUser.get(j).getBalance() >= price) {
                                    allUser.get(j).setBalance(allUser.get(j).getBalance()-price);
                                    allMerchantStocks.get(i).setStock(allMerchantStocks.get(i).getStock()-1);
                                    for (int k = 0; k < allProducts.size(); k++) {
                                        if (allProducts.get(k).getId().equals(productID)) {
                                            if (buyedProduct == null) {
                                                buyedProduct.setUserID(allUser.get(j).getId());
                                                buyedProduct.setProductID(allProducts.get(k).getId());
                                                buyedProduct.setCategoryID(allProducts.get(k).getCategoryID());
                                                buyedProductRepository.save(buyedProduct);
                                                //allUser.get(j).setBuyedProducts(new ArrayList<Product>());
                                                //allUser.get(j).addBuyedProduct(allProducts.get(k));
                                            }else {
                                                buyedProduct.setUserID(allUser.get(j).getId());
                                                buyedProduct.setProductID(allProducts.get(k).getId());
                                                buyedProduct.setCategoryID(allProducts.get(k).getCategoryID());
                                                buyedProductRepository.save(buyedProduct);
                                            }
                                            addlateLoyaltyPoints(userID,productID);
                                            return 1;
                                        }
                                    }
                                }return 3;//balance not
                            }return 5;
                        }return 5;
                    }else {return 4;}
                }
                else {return 2;}
            }
        }
        return 6;
    }

    //First extra endpoint
    public List<Recommendations> recommendations(Integer userID){
        Recommendations allRecommendations = new Recommendations();
        List<Product> allProducts = productRepository.findAll();
        List<User> allUser = userRepository.findAll();
        List<BuyedProduct> buyedProducts = buyedProductRepository.findAll();
        List<Recommendations> recommendationsList = recommendationsRepository.findAll();
        for (int i = 0; i < allUser.size(); i++) {
            if (allUser.get(i).getId() == userID ) {
                for (int k = 0; k < buyedProducts.size(); k++) {
                    if (buyedProducts.get(k).getUserID() == userID) {
                        for (int j = 0; j < allProducts.size(); j++) {
                            if (allProducts.get(j).getCategoryID() == buyedProducts.get(k).getCategoryID()) {
                                allRecommendations.setName(allProducts.get(j).getName());
                                allRecommendations.setPrice(allProducts.get(j).getPrice());
                                allRecommendations.setCategoryID(allProducts.get(j).getCategoryID());
                                allRecommendations.setUserID(allUser.get(i).getId());
                                recommendationsRepository.save(allRecommendations);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return recommendationsRepository.findAll();
    }
    //Second extra endpoint
    public void addlateLoyaltyPoints(Integer userID,Integer productID){
        Integer numberOfYears=0;
        Integer price =0;
        List<Product> allProducts = productRepository.findAll();
        List<User> allUser = userRepository.findAll();
        for (int i = 0; i < allUser.size(); i++) {
            if (allUser.get(i).getId() == userID ) {
                numberOfYears = Period.between(allUser.get(i).getRegistrationDate(),LocalDate.now()).getYears();
                for (int j = 0; j < allProducts.size(); j++) {
                    if (allProducts.get(j).getId() == productID ) {
                        price = allProducts.get(j).getPrice();
                    }
                }
            }
        }
        if (numberOfYears > 7) {
            numberOfYears = 7;
        }
        if (numberOfYears == 0){
            numberOfYears = 1;
        }
        Integer points = (int)((price / 10) * numberOfYears) ;
        for (int i = 0; i < allUser.size(); i++) {
            if (allUser.get(i).getId() == userID ) {
                allUser.get(i).setPoints(allUser.get(i).getPoints()+points);
            }
        }
    }

    // extra credit
    public Boolean login(String userName,String password){
        List<User> allUser = userRepository.findAll();
        for (int i = 0; i < allUser.size(); i++) {
            if (allUser.get(i).getUserName().equals(userName)) {
                if (allUser.get(i).getPassword() == password ) {
                    return true;
                }
            }
        }
        return false;
    }

    //Fourth extra endpoint
    public Integer mergeTowCategory(Integer userID,Integer categoryId1,Integer categoryId2){
        boolean category1 = false;
        boolean category2 = false;
        List<Product> allProducts = productRepository.findAll();
        List<User> allUser = userRepository.findAll();
        for (Category c:categoryRepository.findAll()){
            if (c.getId() == categoryId1) {
                category1=true;
            }
        }
        for (Category c:categoryRepository.findAll()){
            if (c.getId() == categoryId2) {
                category2=true;
            }
        }
        for (int i = 0; i < allUser.size(); i++) {
            if (allUser.get(i).getId().equals(userID)) {
                if (allUser.get(i).getRole().equalsIgnoreCase("Admin")){

                }else {
                    return 0;
                }
            }
        }
        if (category1){
            if (category2) {
                for (int i = 0; i < allProducts.size(); i++) {
                    if (allProducts.get(i).getCategoryID().equals(categoryId2)) {
                        allProducts.get(i).setCategoryID(categoryId1);
                    }
                }
                categoryRepository.deleteById(categoryId2);
                return 1;
            }
            return 3;
        }
        return 2;
    }

    public String buyByPoint(Integer userID,Integer productID){
        Integer points = 0;
        Integer price = 0;
        List<Product> allProducts = productRepository.findAll();
        List<MerchantStock> allMerchantStocks = merchantStockRepository.findAll();
        List<User> allUser = userRepository.findAll();
        BuyedProduct buyedProduct = new BuyedProduct();
        for (int i = 0; i < allUser.size(); i++) {
            if (allUser.get(i).getId() == userID ) {
                points = allUser.get(i).getPoints();
            }
        }

        Integer pointToMoney = (Integer) (points /100);
        Integer pointsToDeduct = price * 100;
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productID ) {
                if (allProducts.get(i).getPrice() <= pointToMoney) {
                    price = allProducts.get(i).getPrice();
                    for (int j = 0; j < allUser.size(); j++) {
                        if (allUser.get(j).getId() == userID ) {
                            allUser.get(j).setPoints(allUser.get(j).getPoints()-pointsToDeduct);
                            buyedProduct.setUserID(allUser.get(j).getId());
                            buyedProduct.setProductID(allProducts.get(i).getId());
                            buyedProduct.setCategoryID(allProducts.get(i).getCategoryID());
                            buyedProductRepository.save(buyedProduct);
                            return "Product purchased successfully using points!";
                        }
                    }
                }else {
                    return "Not enough points to purchase the product.";
                }
            }
        }
        return "User or product not found.";
    }
}
