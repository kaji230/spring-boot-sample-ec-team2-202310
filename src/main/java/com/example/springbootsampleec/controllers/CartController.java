package com.example.springbootsampleec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.springbootsampleec.repositories.CartRepository;
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
	@Autowired
    private CartRepository cartRepo;

	//カート商品一覧
	@GetMapping("/{id}")
    public String index(
        @PathVariable("id")  Integer id,
        //現在ログイン中のユーザー情報を取得
        @AuthenticationPrincipal(expression = "user") User user,
        Model model) {
		model.addAttribute("user", user);//ログインユーザの取得
        Item item = itemService.findById(id).orElseThrow();
        model.addAttribute("item", item);
        model.addAttribute("main", "carts/cart::main");        
        return "layout/logged_in";    
    }
	
	//カートに入れる
	@PostMapping("/inCart/{itemId}")    
    public String inCart(
        @PathVariable("itemId")  Long itemId,
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
        ) {
        Item item = itemService.findById(itemId).orElseThrow();
        model.addAttribute("item", item);
        int amount=1;
        cartService.register(
            user,
            item,
            amount
        );
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "カートに商品が追加されました！");       
      //redirect(ルーティング)の場合はURLを記述する、そうでない場合はtemplateの場所を記述する
        //return "redirect:/cart/"+ item.getId(); 
        return "redirect:/cart/"+ item.getId();
    }
	
	//削除
	    @GetMapping("/delete/{cartId}")    
	    public String delete(	    		
	        @PathVariable("cartId")  Integer id,
	        @AuthenticationPrincipal(expression = "user") User user,
	        RedirectAttributes redirectAttributes,
	        Model model) {
	    	model.addAttribute("user", user);//ログインユーザの取得
	        cartService.delete(id);
	        System.out.println("aaaaaaaaaaaaaaaaaaaa");
	        Item item = itemService.findById(id).orElseThrow();
	        model.addAttribute("item", item);
	        return "redirect:/cart/"+ item.getId(); 
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
			 

}
