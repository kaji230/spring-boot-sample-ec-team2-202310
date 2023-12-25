package com.example.springbootsampleec.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Review;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.ReviewRepository;
import com.example.springbootsampleec.services.ReviewService;


@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    private Environment environment;
    
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Review> findByItemIdOrderByCreatedAtDesc(Long itemId) {
        return reviewRepository.findByItemIdOrderByCreatedAtDesc(itemId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Review> findByUserIdOrderByCreatedAtDesc(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    @Override
    public void delete(int id) {
    }

    @Transactional
    @Override
    public void register(String comment, String star, Item item, User user) {
    	Review review = new Review(null, item, user, comment, star, null, null);
    	reviewRepository.save(review);
    }
    
    // レビューを更新
    @Transactional
    @Override
    public void updateReview(long id, String star, String comment) {
    	Review review =  findById(id).orElseThrow();
        review.setStar(star);
        review.setComment(comment);
        reviewRepository.saveAndFlush(review);
       
    }
}