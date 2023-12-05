package com.example.springbootsampleec.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.repositories.UserRepository;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.UserService;


@RequestMapping("/cart")
@Controller
public class CartController {
	private final CartService cartService;
	private final ItemService itemService;
	private final UserService userService;
	@Autowired
    private ItemRepository itemRepository;
	private UserRepository userRepository;
	
   
	public  CartController(
	        CartService cartService,
	        ItemService itemService,
	        UserService userService
	    ) {
	        this.cartService = cartService;
	        this.itemService = itemService;
	        this.userService = userService;
	    }
	@GetMapping("/")    
    public String index(
        @AuthenticationPrincipal(expression = "user") User user,
        @AuthenticationPrincipal(expression = "item") Item item,
        Model model
    ) {
    	List<Cart> carts = cartService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("item", item);
        model.addAttribute("carts", carts);
        return "carts/cart";    
    }
	
	/*
	 @PostMapping("/amount_size")    
	    public String in_cart(
	    		//@PathVariable("id")  Integer id,
	    		@Valid AmountForm amountForm,
	    		@AuthenticationPrincipal(expression = "user") User user,
	    		@AuthenticationPrincipal(expression = "item") Item item,
	    		Cart cart,
	    		Model model
	    ) {
		 model.addAttribute("amountForm", amountForm);
		 int amount = amountForm.getAmount_size();
		 cartService.register(
				 	user,
				 	item,
		            amount	            
		        );
		 return "redirect:/cart";
	 }
	 */
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
