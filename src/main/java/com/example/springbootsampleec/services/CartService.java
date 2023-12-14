package com.example.springbootsampleec.services;

import java.util.List;
import java.util.Optional;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;

public interface CartService {
	// カート内商品一覧の取得
    List<Cart> findAll();
    
    //IDを指定してカート内商品を取得
    Optional<Cart> findById(long id);
    
    //カート内商品数を取得
   // int getAmount();

    // 削除
    void delete(long id);
    
    // 商品の登録
    void register(User user, Item item, int amount);	

}
