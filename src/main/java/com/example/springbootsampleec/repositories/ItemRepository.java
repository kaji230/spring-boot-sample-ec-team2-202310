package com.example.springbootsampleec.repositories;
 
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springbootsampleec.entities.Item;
 
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByNameContaining(String keyword);
	
	//登録日時の新しい商品３件を検索
	List<Item> findTop3ByOrderByCreatedAtDesc();
	
	//ランダムに商品を３件検索
	@Query(value = "SELECT * FROM item ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<Item> findRandom3Records();
	
	//商品の種類の同じ商品の一覧を検索
	List<Item> findByType(int type);
	
	//名前でひとつの商品を検索
	Item findFirst1ByNameContaining(String keyword);
	
	//ページネーション
	public Page<Item> findAll(Pageable pageable);
}