package com.example.capstone1amazonupdated.Service;

import com.example.capstone1amazonupdated.Model.Category;
import com.example.capstone1amazonupdated.Model.Merchant;
import com.example.capstone1amazonupdated.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public List<Merchant> getMerchants() {
        return merchantRepository.findAll();
    }
    public void addMerchants(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public Boolean updateMerchant(Integer id,Merchant merchant) {
        List<Merchant> allEmployee = merchantRepository.findAll();
        for(Merchant m : allEmployee){
            if(id == m.getId()){
                m.setName(merchant.getName());
                merchantRepository.save(m);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteMerchant(Integer id) {
        List<Merchant> allEmployee = merchantRepository.findAll();
        for(Merchant m : allEmployee){
            if(id == m.getId()){
                merchantRepository.delete(m);
                return true;
            }
        }
        return false;
    }
}
