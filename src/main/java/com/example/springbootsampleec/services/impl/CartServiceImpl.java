package com.example.springbootsampleec.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.repositories.CartRepository;
import com.example.springbootsampleec.services.CartService;

@Service
public class CartServiceImpl implements CartService{
	private final CartRepository cartRepository;
	
	public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
	
	@Transactional(readOnly = true)
    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
    

    @Transactional(readOnly = true)
    @Override
    public Optional<Cart> findById(long id) {
        return cartRepository.findById(id);
    }
    @Transactional
    @Override
    public void register(int user_id, int item_id, int amount) {
    	// Cart エンティティの生成
        Cart cart = new Cart(null,null, null , amount, null, null);
        
         // Cart を保存
         cartRepository.saveAndFlush(cart);
       }
    
    //削除
    @Transactional
    @Override
    public void delete(long id) {
        Cart cart =  findById(id).orElseThrow();
        cartRepository.delete(cart);
    }
 
	

}
