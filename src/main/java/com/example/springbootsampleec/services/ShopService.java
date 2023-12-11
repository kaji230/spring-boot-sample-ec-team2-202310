package com.example.springbootsampleec.services;
 
import com.example.springbootsampleec.entities.Shop;
 
public interface ShopService {
	Shop findById(long id);
};