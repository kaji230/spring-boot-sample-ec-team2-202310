package com.example.springbootsampleec.services;
 
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
 
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
 
import java.util.List;
 
public interface ItemService {
    // 投稿一覧の取得
    List<Item> findAll();
    //nameで検索
    List<Item> findByNameContaining(String keyword);
    // ID を指定して投稿を取得
    Optional<Item> findById(long id);
    
    // ID を指定して商品のIDを取得
    //Item findByItemId(long id);
    
    // 商品情報を更新
    void updateItem(long id, String name, int price, int stock, String description,
    		MultipartFile image, MultipartFile img_1, MultipartFile img_2, MultipartFile img_3);
    // 削除
    void delete(long id);
    // 商品の登録
    void register(int shop_id, String name, int price, int stock, int type, String description,
    		MultipartFile image, MultipartFile img_1, MultipartFile img_2, MultipartFile img_3);
    //いいね処理
    void toggleLike(User user, long item_id);
}