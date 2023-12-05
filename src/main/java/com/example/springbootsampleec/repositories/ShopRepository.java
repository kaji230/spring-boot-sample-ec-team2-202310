package com.example.springbootsampleec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.springbootsampleec.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>{
	List<Shop>findAll();
	
	@Query("SELECT s.id FROM Shop s")
    List<Long> findAllShopIds();
}