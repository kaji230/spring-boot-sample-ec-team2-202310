package com.example.springbootsampleec.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.UserService;


@RequestMapping("/cart")
@Controller
public class CartController {
	private final CartService cartService;
	private final UserService userService;
	private ItemRepository itemRepo;
	
	public  CartController(
	        CartService cartService,
	        UserService userService
	    ) {
	        this.cartService = cartService;
	        this.userService = userService;
	    }
	
	 @GetMapping("/")    
	    public String index(
	        @AuthenticationPrincipal(expression = "user") User user,
	        Model model
	    ) {
		// 最新のユーザー情報を取得
	        Optional<User> refreshedUser = userService.findById(user.getId());
	    	List<Cart> carts = cartService.findAll();
	        model.addAttribute("carts", carts);
	        model.addAttribute("item", itemRepo.findAll());
	        model.addAttribute("title", "購入商品一覧");
	        //model.addAttribute("main", "cart::main");
	        return "layout/logged_in";    
	    }
	 
	//削除
	    @PostMapping("/delete/{id}")    
	    public String delete(
	        @PathVariable("id")  Integer id,
	        RedirectAttributes redirectAttributes,
	        Model model) {
	        cartService.delete(id);
	        return "redirect:/cart/";  
	    }

}
