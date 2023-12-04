package com.example.springbootsampleec.services;

import java.util.List;
import java.util.Optional;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;

public interface CartService {
	// カート内商品一覧の取得
    List<Cart> findAll();
 // ID を指定して投稿を取得
    Optional<Cart> findById(long id);
 // 削除
    void delete(long id);
 // 購入商品数の登録
    void register(User user, Item item, int amount);
 //ユーザーが登録した商品の取得
    List<Cart>getCartItems(User user);

}
