package com.example.capstone1amazonupdated.Service;

import com.example.capstone1amazonupdated.Model.Category;
import com.example.capstone1amazonupdated.Model.Product;
import com.example.capstone1amazonupdated.Repository.CategoryRepository;
import com.example.capstone1amazonupdated.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    //ArrayList<Product> products = new ArrayList<>();
    private final ProductRepository productRepository;
    //private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public List<Product> getProduct(){
        return productRepository.findAll();
    }


    ////////////////////////////////
    public Boolean addProduct(Product product){
        List<Category> allCategories = categoryRepository.findAll();
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getId() == product.getCategoryID()) {
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    public Boolean updateProduct(Integer id, Product product){
        List<Product> allProduct = productRepository.findAll();
        for (int i = 0; i < allProduct.size(); i++) {
            if(allProduct.get(i).getId() == id ){
                allProduct.get(i).setName(product.getName());
                allProduct.get(i).setPrice(product.getPrice());
                allProduct.get(i).setCategoryID(product.getCategoryID());
                productRepository.save(allProduct.get(i));
                return true;
            }
        }
        return false;
    }

        public Boolean deleteProduct(Integer id){
        List<Product> allProduct = productRepository.findAll();
        for (int i = 0; i < allProduct.size(); i++) {
            if(allProduct.get(i).getId() == id){
                productRepository.delete(allProduct.get(i));
                return true;
            }
        }
        return false;
    }

}
