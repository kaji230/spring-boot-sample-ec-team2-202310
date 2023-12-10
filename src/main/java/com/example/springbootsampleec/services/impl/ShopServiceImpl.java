package com.example.springbootsampleec.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootsampleec.entities.Shop;
import com.example.springbootsampleec.repositories.ShopRepository;
import com.example.springbootsampleec.services.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop findById(long id) {
        return shopRepository.findById(id).orElse(null);
    }
}
