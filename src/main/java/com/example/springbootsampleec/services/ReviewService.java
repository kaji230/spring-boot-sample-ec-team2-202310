package com.example.springbootsampleec.services;

import java.util.List;
import java.util.Optional;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Review;
import com.example.springbootsampleec.entities.User;

public interface ReviewService {
	List<Review> findByItemIdOrderByCreatedAtDesc(Long itemId);
	
	//ユーザーのレビュー一覧
	List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
	
	//Idでレビューを取得
	Optional<Review> findById(Long id);
	
	// 削除
    void delete(int id);
    // 投稿の登録
    void register(String comment, String star, Item item, User user);
    
    // レビューを更新
    void updateReview(long id, String star, String comment);
};