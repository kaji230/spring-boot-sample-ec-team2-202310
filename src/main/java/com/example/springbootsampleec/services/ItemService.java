package com.example.springbootsampleec.services;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Shop;
import com.example.springbootsampleec.entities.User;
 
public interface ItemService {
	//idを指定して商品ストックの取得
	Optional<Item> findByStock(long id);
		
    // 投稿一覧の取得
    List<Item> findAll();
    
    //nameで検索
    List<Item> findByNameContaining(String keyword);
   
    // ID を指定して投稿を取得
    Optional<Item> findById(long id);
   
    // 商品情報を更新
    void updateItem(long id, String name, int price, int stock, String description,
    		MultipartFile image, MultipartFile img_1, MultipartFile img_2, MultipartFile img_3);
    // 削除
    void delete(long id);
   
    // 商品の登録
    void register(Shop shop, String name, int price, int stock, int type, String description,
    		MultipartFile image, MultipartFile img_1, MultipartFile img_2, MultipartFile img_3);
   
    //いいね処理
    void toggleLike(User user, long item_id);
    
    //登録日時の新しい商品３件を検索
  	List<Item> findTop3ByOrderByCreatedAtDesc();
  	
  	//ランダムに商品を３件検索
  	@Query(value = "SELECT * FROM item ORDER BY RAND() LIMIT 3", nativeQuery = true)
  	List<Item> findRandom3Records();
  	
  	//商品の種類の同じ商品の一覧を検索
  	List<Item> findByType(int type);
  	
    //名前でひとつの商品を検索
  	Item findFirst1ByNameContaining(String keyword);
}