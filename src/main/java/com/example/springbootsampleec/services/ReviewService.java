package com.example.springbootsampleec.services;

import java.util.List;

import com.example.springbootsampleec.entities.Review;

public interface ReviewService {
	List<Review> findByItemIdOrderByCreatedAtDesc(Long itemId);
	// 削除
    void delete(int id);
    // 投稿の登録
    void register(int id, String comment, int star);
};