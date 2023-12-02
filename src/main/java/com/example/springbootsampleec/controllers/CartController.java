package com.example.springbootsampleec.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.UserService;


@RequestMapping("/cart")
@Controller
public class CartController {
	private final CartService cartService;
	private final UserService userService;
	
   
	public  CartController(
	        CartService cartService,
	        UserService userService
	    ) {
	        this.cartService = cartService;
	        this.userService = userService;
	    }
	
	 @PostMapping("/")    
	    public String index(
	    		//@PathVariable("id")  Integer id,
	    		// @RequestParam int amount,
	    		@AuthenticationPrincipal(expression = "user") User user,
	    		@AuthenticationPrincipal(expression = "item") Item item,
	    		//int amount,
	    		Cart cart,
	    		Model model
	    ) {
		 //Optional<User> user_id = userService.findById(user.getId());
		 int amountSize=1;
		 cartService.register(
		            user,
		            item,
		            amountSize
		        );
		// 最新のカート情報を取得
	    	List<Cart> carts = cartService.findAll();
	    	model.addAttribute("user", user);
	        model.addAttribute("carts", carts);
	        model.addAttribute("title", "購入商品一覧");
	        model.addAttribute("main", "carts/cart::main");
	        return "layout/logged_in";    
	    }
	 
	//削除
	    @GetMapping("/delete/{id}")    
	    public String delete(
	        @PathVariable("id")  Integer id,
	        RedirectAttributes redirectAttributes,
	        Model model) {
	        cartService.delete(id);
	        return "redirect:/cart/";  
	    }

}
