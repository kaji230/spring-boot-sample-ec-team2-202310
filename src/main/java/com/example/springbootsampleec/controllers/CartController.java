package com.example.springbootsampleec.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.forms.AmountForm;
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
	
	//カートに追加
	@PostMapping("/")    
    public String addToCart(
    	@RequestParam("itemId") Long id,
        Model model
    ) {
       //どの商品が追加されたのか特定する
        Item addToItem = itemService.findByItemId(id);
        model.addAttribute("addtoItem", addToItem);
        return "carts/cart";    
    }
	@GetMapping("/amountSize")    
    public String amountSize(
        @ModelAttribute("amountForm") AmountForm amountForm,
        Model model
        ) {
        model.addAttribute("amount", "amountForm");
        return "carts/cart";    
    }
	
	 @PostMapping("/amountSize")
	 public String amountSize(
			 @Valid AmountForm amountForm,
			 BindingResult bindingResult,
			 RedirectAttributes redirectAttributes,
			 @AuthenticationPrincipal(expression = "user") User user,
			 @AuthenticationPrincipal(expression = "user") Item item,
			 Model model
			 ) {
		 if(bindingResult.hasErrors()){
			 return amountSize(amountForm, model);
		 }
		 cartService.register(
				 user,
				 item,
				 amountForm.getAmount_size()
				 );
		 return "redirect:/carts/cart";
	 }
	/*
	 @GetMapping("/")
	    public String index(Model model) {
	        List<Item> items = itemRepository.findAll();
	        model.addAttribute("items", items);
	        return "items";
	    }

	    @PostMapping("/addToCart")
	    public String addToCart(
	    		@RequestParam Long itemId, 
	    		@RequestParam int amountSize,
	    		@AuthenticationPrincipal(expression = "user") User user
	    		) {
	     
	        // 商品をカートに追加
	        Item item = itemRepository.findById(itemId).orElse(null);
	        cartService.register(user, item, amountSize);
	        return "redirect:/cart";
	    }
	    */
	 
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
