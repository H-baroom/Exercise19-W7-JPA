package com.example.capstone1amazonupdated.Service;

import com.example.capstone1amazonupdated.Model.Category;
import com.example.capstone1amazonupdated.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    //ArrayList<Category> categories = new ArrayList<>();

    private final CategoryRepository categoryRepository;


    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public void addCategories(Category category){
        categoryRepository.save(category);
    }

    public Boolean updateCategory(Integer id,Category category){
        List<Category> allCategory = categoryRepository.findAll();
        for(Category c : allCategory){
            if(id.equals(c.getId())){
                c.setName(category.getName());
                categoryRepository.save(c);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteCategory(Integer id){
        List<Category> allCategory = categoryRepository.findAll();
        for(Category c : allCategory){
            if(id == c.getId()){
                categoryRepository.delete(c);
                return true;
            }
        }
        return false;
    }


}
