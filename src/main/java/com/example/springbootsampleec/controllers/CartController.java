package com.example.springbootsampleec.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
//import com.example.springbootsampleec.forms.AmountForm;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.UserService;


@RequestMapping("/cart")
@Controller
public class CartController {
	//各テーブルのサービスを利用できるように
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
	
	//カートの中身一覧表示
	@GetMapping("/")    
    public String index(
    		// user にはログイン中のユーザーの情報が入っている
    		 @AuthenticationPrincipal(expression = "user") User user,
    	        Model model
    ) {
		// 最新のユーザー情報を取得
        Optional<User> refreshedUser = userService.findById(user.getId());
        model.addAttribute("user", refreshedUser.orElseThrow());
		List<Cart> carts = cartService.findAll();
		model.addAttribute("user", user);
        //model.addAttribute("cart", carts);
        return "carts/cart/";    
    }
	
	//カートに入れる
	@PostMapping("/inCart/{itemId}")    
    public String inCart(
    	//@ModelAttribute User user,
        //BindingResult bindingResult,
        @PathVariable("itemId")  Long itemId,
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
        ) {
        Item item = itemService.findById(itemId).orElseThrow();
        int amount=1;
        cartService.register(
            user,
            item,
            amount
        );
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "コメントの投稿が完了しました");
        return "redirect:/carts/cart/" + item.getId();  
    }
	/*
	@GetMapping("/amountSize")    
    public String amountSize(
        @ModelAttribute("amountForm") AmountForm amountForm,
        Model model
        ) {
        model.addAttribute("amount", "amountForm");
        return "carts/cart/";    
    }
    */
		 
	//削除
	    @GetMapping("/delete/{id}")    
	    public String delete(
	        @PathVariable("id")  Integer id,
	        RedirectAttributes redirectAttributes,
	        Model model) {
	        cartService.delete(id);
	        return "redirect:carts/cart/";  
	    }

}
