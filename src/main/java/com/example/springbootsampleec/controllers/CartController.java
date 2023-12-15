package com.example.springbootsampleec.controllers;

import java.util.List;
import java.util.Optional;

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
//import com.example.springbootsampleec.forms.AmountForm;
import com.example.springbootsampleec.repositories.CartRepository;
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
        //@Valid AmountForm amountForm,//数量追記
        //現在ログイン中のユーザー情報を取得
        @AuthenticationPrincipal(expression = "user") User user,
        Model model) {
		model.addAttribute("user", user);//ログインユーザの取得		
		//ログインユーザーのカート情報を取得して表示
		User userId = userService.findById(id).orElseThrow();
		//カート内の商品合計
		int subtotal = 0;
		int total = 0;
		for(Cart cartItem : userId.getCarts()) {
		subtotal = (cartItem.getItem().getPrice() * cartItem.getAmount());
		total += subtotal;
		}
		model.addAttribute("total", total);		
		model.addAttribute("user", userId);
        model.addAttribute("main", "carts/cart::main");        
        return "layout/logged_in";    
    }
	
	//カートに入れる
	@PostMapping("/inCart/{itemId}")    
    public String inCart(
    	//@Valid AmountForm amountForm,//数量追記
        @PathVariable("itemId")  Long itemId,
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
        ) {
        Item item = itemService.findById(itemId).orElseThrow();
        model.addAttribute("item", item);        
        //選択した商品がすでにカートにあるかをみる
        Optional<Cart> checkItems = cartRepo.findByUserAndItem(user, item);
        if (checkItems.isPresent()) {
        	// 既存のエントリが存在する場合は数量を更新
        	Cart cart = checkItems.get();
        	cart.setAmount(cart.getAmount() + 1);
        	cartRepo.saveAndFlush(cart);
        	} else {
        	// 既存のエントリが存在しない場合は新しくcartテーブルにエントリを作成
        		int amount=1;
        	cartService.register(
        			user,
        			item,
        			amount
        			);
        	}
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "カートに商品が追加されました！");       
        //redirect(ルーティング)の場合はURLを記述する、そうでない場合はtemplateの場所を記述する
        return "redirect:/cart/"+ user.getId();
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
	        return "redirect:/cart/"+ user.getId(); 
	    }
	    
	    //商品数選択(余力があればカートページからプルダウンで数量変更ができるようにしたい！！岩井)
		
	    /*@PostMapping("/amountSize/{cartId}")    
	    public String amountSize(
	        @ModelAttribute("amountForm") AmountForm amountForm,
	        @PathVariable("cartId")  Integer id,
	        @AuthenticationPrincipal(expression = "user") User user,
	        RedirectAttributes redirectAttributes,
	        Model model
	        ) {
			model.addAttribute("user", user);//ログインユーザの取得
			int amountSize = cartService.getAmountSize(id);
			System.out.println(amountSize);
			cartService.register(
		            user,
		            item,
		            amountSize
		        );		        
			int total = itemService.getPrice(id)*amountSize;
			return "redirect:/cart/"+ user.getId();   
	    }*/
			 

}
