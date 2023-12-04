package com.example.springbootsampleec.controllers;

import java.util.List;

import javax.validation.Valid;

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
import com.example.springbootsampleec.forms.AmountForm;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.UserService;


@RequestMapping("/cart")
@Controller
public class CartController {
	private final CartService cartService;
	private final ItemService itemService;
	private final UserService userService;
	
   
	public  CartController(
	        CartService cartService,
	        ItemService itemService,
	        UserService userService
	    ) {
	        this.cartService = cartService;
	        this.itemService = itemService;
	        this.userService = userService;
	    }
	
	 @PostMapping("/create")    
	    public String in_cart(
	    		//@PathVariable("id")  Integer id,
	    		@Valid AmountForm amountForm,
	    		@AuthenticationPrincipal(expression = "user") User user,
	    		@AuthenticationPrincipal(expression = "item") Item item,
	    		Cart cart,
	    		Model model
	    ) {
		 //Optional<User> user_id = userService.findById(user.getId());
		 //int amountSize=1;
		 int amount = amountForm.getAmount_size();
		 cartService.register(
		            amount	            
		        );
		 return "redirect:/cart";
	 }
		 
		 @GetMapping("/")    
		 public String index(
				 @AuthenticationPrincipal(expression = "user") User user,
			        Model model) {
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
