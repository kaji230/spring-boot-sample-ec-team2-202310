package com.example.springbootsampleec.repositories;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootsampleec.entities.Review;
 
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	//レビューをすべて取得、作成日順に並び替え
	List<Review> findByItemIdOrderByCreatedAtDesc(Long itemId);
	//ユーザーのレビュー一覧に使用
	List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
	
	
}