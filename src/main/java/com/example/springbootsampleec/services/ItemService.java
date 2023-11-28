package com.example.springbootsampleec.services;
 
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.springbootsampleec.entities.Item;
 
public interface ItemService {
    // 投稿一覧の取得
    List<Item> findAll();
    //nameで検索
    List<Item> findByNameContaining(String keyword);
    // ID を指定して投稿を取得
    Optional<Item> findById(long id);
    // 商品情報を更新
    void updateItem(long id, String name, int price, int stock, String description);
    // 削除
    void delete(long id);
    // 投稿の登録
    void register(String name, int price, int stock, String description, MultipartFile image);
}