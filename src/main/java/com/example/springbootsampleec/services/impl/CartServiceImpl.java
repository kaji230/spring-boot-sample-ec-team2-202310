package com.example.springbootsampleec.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.CartRepository;
import com.example.springbootsampleec.services.CartService;
@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private final CartRepository cartRepository;
	
	@Autowired
    private Environment environment;// 環境変数を使えるように。
	
	public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    } 
	
	//カート一覧表示
	 @Transactional(readOnly = true)
	    @Override
	    public List<Cart> findAll() {
	        return cartRepository.findAll();
	    }
	 
	//IDを指定してカート内商品取得
		 @Transactional(readOnly = true)
		    @Override
		    public List<Cart> findById() {
		        return cartRepository.findById();
		    }
    
    //カートに登録
    @Transactional(readOnly = false)
    @Override
    public void register(User user, Item item, int amount) {
    	 // Cart エンティティの生成
         Cart cart = new Cart(null,user, item, amount, null, null);        
         // Cart を保存
         cartRepository.saveAndFlush(cart);
       }
    
    //商品削除
    @Transactional
    @Override
    public void delete(long id) {
        Cart cart =  findById(id).orElseThrow();
        cartRepository.delete(cart);
    }
 
	

}
