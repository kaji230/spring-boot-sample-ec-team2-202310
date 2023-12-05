package com.example.springbootsampleec.repositories;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springbootsampleec.entities.Item;
 
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByNameContaining(String keyword);
	
	//登録日時の新しい商品３件を検索
	List<Item> findTop3ByOrderByCreatedAtDesc();
	
	//IDを指定して商品のIDを取得
	Item findByItemId(Long id);
	
	//ランダムに商品を３件検索
	@Query(value = "SELECT * FROM your_entity_table ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<Item> findRandom3Records();
}