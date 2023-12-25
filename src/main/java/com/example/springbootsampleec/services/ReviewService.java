package com.example.springbootsampleec.services;

import java.util.List;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Review;
import com.example.springbootsampleec.entities.User;

public interface ReviewService {
	List<Review> findByItemIdOrderByCreatedAtDesc(Long itemId);
	
	//ユーザーのレビュー一覧
	List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
	
	// 削除
    void delete(int id);
    // 投稿の登録
    void register(String comment, String star, Item item, User user);
    //　投稿の更新
    void updateReview(long id, String star, String comment);
};