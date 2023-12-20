package com.example.springbootsampleec.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.springbootsampleec.forms.AmountForm;
import com.example.springbootsampleec.repositories.CartRepository;
import com.example.springbootsampleec.repositories.ItemRepository;
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
	@Autowired
	private ItemRepository itemRepo;

	//カート商品一覧
	@GetMapping("/{id}")
    public String index(
        @PathVariable("id")  Integer id,
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
        return "layout/logged_in_simple";    
    }
	
	//プルダウンから購入商品数選択
	@PostMapping("/{id}/amountForm")
	public String amountForm(
	        @PathVariable("id") Integer id,
	        RedirectAttributes redirectAttributes,
	        @AuthenticationPrincipal(expression = "user") User user,
	        @ModelAttribute AmountForm amountForm,
	        BindingResult result,
	        Model model
	        ) {
		model.addAttribute("amountForm",amountForm);
		//カートから商品が削除されたら、商品ストックに増やされる	    	
    	Cart cart = cartService.findById(id).orElseThrow();//ログインユーザーのカート情報を取得
    	int presentAmountSize = cart.getAmount();//購入済商品の現在カートに入れてる商品数
    	Item item = cart.getItem();
    	int checkStock = item.getStock();//現在の商品在庫数
    	int selectedAmount = amountForm.getAmountSize();//選択した商品数
    	//商品在庫がある時
    	if (checkStock > 0) {
        	cart.setAmount(selectedAmount);
        	cartRepo.saveAndFlush(cart);
        	//商品テーブルの商品ストックから購入商品数を減らす
        	int newItemSize = checkStock - selectedAmount;
    		item.setStock(newItemSize);
    		itemRepo.saveAndFlush(item);
    	}else if(checkStock == 0){
    		redirectAttributes.addFlashAttribute(
					"You don't buy a item. Sorry...",
	        		"在庫がありません。");
        } 
		return "redirect:/cart/"+ user.getId();
    	}
		
	//カートに入れる
	@PostMapping("/inCart/{itemId}")    
    public String inCart(
        @PathVariable("itemId") Integer itemId,
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
        ) {
        Item item = itemService.findById(itemId).orElseThrow();
        model.addAttribute("item", item);        
        //ログインユーザーが選択した商品がすでにカートにあるかをみる
        Optional<Cart> checkItems = cartRepo.findByUserAndItem(user, item);
        int ItemSize = item.getStock();
        //ログインユーザーが選択した商品がすでにカートにあり、且つ在庫がある時
        if (checkItems.isPresent() && ItemSize > 0) {
        	//既存のエントリが存在する場合は数量+1する       	
        	Cart cart = checkItems.get();
        	cart.setAmount(cart.getAmount() + 1);
        	cartRepo.saveAndFlush(cart);        	
        	//商品テーブルの商品ストックから-1する
        	int newItemSize = ItemSize - 1;
    		item.setStock(newItemSize);
    		itemRepo.saveAndFlush(item);
    	//ログインユーザーが選択した商品がすでにカートにあるが在庫がない時
        } else if (checkItems.isPresent() && ItemSize == 0) {
        	redirectAttributes.addFlashAttribute(
					"You don't buy a item. Sorry...",
	        		"在庫がありません。");
        } else {
        		// 既存のエントリが存在しない場合は新しくcartテーブルにエントリを作成
        		int amount=1;
        		cartService.register(
        				user,
        				item,
        				amount
        				);
        		int newItemSize = ItemSize - 1;
        		item.setStock(newItemSize);
        		itemRepo.saveAndFlush(item);
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
	    	
	    	//カートから商品が削除されたら、商品ストックに増やされる	    	
	    	Cart cart = cartService.findById(id).orElseThrow();//ログインユーザーのカート情報を取得
	    	int presentAmountSize = cart.getAmount();//購入済商品の現在カートに入れてる商品数
	    	Item itemId = cart.getItem();
	    	int checkStock = itemId.getStock();//現在の商品在庫数
	    	itemId.setStock(checkStock+presentAmountSize);//カートから商品が削除されたら在庫が増える
	    	cartService.delete(id);
	        return "redirect:/cart/"+ user.getId(); 
	    }
	    
	  //購入完了
	    @PostMapping("/purchased/{userId}")
	    public String purchased(
	    		@PathVariable("userId")  Integer id,
	    		//現在ログイン中のユーザー情報を取得
	    		@AuthenticationPrincipal(expression = "user") User user,
	    		Model model	) {
	    	model.addAttribute("user", user);//ログインユーザの取得
	    	//ログインユーザーのカート情報を取得して表示
			User userId = userService.findById(id).orElseThrow();
	        for(Cart cartItem : userId.getCarts()) {
	        	cartService.delete(cartItem.getId());
	        	}
	        model.addAttribute("user", user);
	        return "carts/purchased";  	    	
	    }
	
}
