package com.example.springbootsampleec.controllers;
 
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Review;
import com.example.springbootsampleec.entities.Shop;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.forms.ItemCreateForm;
import com.example.springbootsampleec.forms.ItemEditForm;
import com.example.springbootsampleec.forms.ReviewCreateForm;
import com.example.springbootsampleec.repositories.ShopRepository;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.ReviewService;
import com.example.springbootsampleec.services.ShopService;
 
@RequestMapping("/items")
@Controller
public class ItemController { 
    private final ItemService itemService;
	private final ReviewService reviewService;
	private final ShopService shopService;
    
    public ItemController(
        ItemService itemService,
        ReviewService reviewService,
        ShopService shopService
    ) {
        this.itemService = itemService;
        this.reviewService = reviewService;
        this.shopService = shopService;
    }
    
    @Autowired
    private ShopRepository shopRepository;
    
    @GetMapping("/")    
    public String top(
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
    ) {
    	List<Item> items = itemService.findAll();
    	List<Item> newItems = itemService.findTop3ByOrderByCreatedAtDesc();
    	List<Item> randomItems = itemService.findRandom3Records();
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        model.addAttribute("newItems", newItems);
        model.addAttribute("randomItems", randomItems);
        model.addAttribute("title", "商品一覧");
        model.addAttribute("main", "items/top::main");
        return "layout/logged_in";    
    }
    
    @GetMapping("/index")    
    public String index(
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
    ) {
    	List<Item> items = itemService.findAll();
    	
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        model.addAttribute("title", "商品一覧");
        model.addAttribute("main", "items/index::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/create")
    public String create(
        @AuthenticationPrincipal(expression = "user") User user,
        @ModelAttribute("itemCreateForm") ItemCreateForm itemCreateForm,
        Model model
    ) {
    	List<Shop> shops = shopRepository.findAll();

    	model.addAttribute("shops", shops);
        model.addAttribute("title", "商品の新規作成");
        model.addAttribute("user", user);
        model.addAttribute("main", "items/create::main");
        return "layout/logged_in";
    }
    
    @PostMapping("/create")    
    public String createProcess(
        @AuthenticationPrincipal(expression = "user") User user,
        @Valid ItemCreateForm itemCreateForm,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult,
        Model model
        ) {
        if(bindingResult.hasErrors()){
            return create(user, itemCreateForm, model);
        }
       
        itemService.register(
        		itemCreateForm.getSelectedShop(),
                itemCreateForm.getName(),
                itemCreateForm.getPrice(),
                itemCreateForm.getStock(),
                itemCreateForm.getType(),
                itemCreateForm.getDescription(),
                itemCreateForm.getImage(),
                itemCreateForm.getImg_1(),
                itemCreateForm.getImg_2(),
                itemCreateForm.getImg_3()
        );
        
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品を追加しました");
        
        return "redirect:/admin";
    }
    
    @RequestMapping("/detail/{id}/create_review")
    public String create_review(
    	@AuthenticationPrincipal(expression = "user") User user,
    	@PathVariable("id")  Long id,
    	@ModelAttribute ReviewCreateForm reviewCreateForm,
    	Model model
	) {
    	Item item = itemService.findById(id).orElseThrow();
    	
    	
    	model.addAttribute("item", item);
    	model.addAttribute("user", user);
    	model.addAttribute("reviewCreateForm", reviewCreateForm);
    	model.addAttribute("main", "items/create_review::main");
    	return "layout/logged_in";
    }
    
    @PostMapping("/detail/{id}/create_review")
    public String reviewProcess(
    	@AuthenticationPrincipal(expression = "user") User user,
    	@PathVariable("id")  Long id,
    	@ModelAttribute ReviewCreateForm reviewCreateForm,
    	Model model
	) {
    	Item item = itemService.findById(id).orElseThrow();
    	Long itemId = item.getId();
    	Long userId = user.getId();
    	reviewService.register(
    			reviewCreateForm.getComment(),
    			reviewCreateForm.getStar(),
    			item,
    			user
    			);
    	
    	model.addAttribute("item", item);
    	model.addAttribute("user", user);
    	model.addAttribute("reviewCreateForm", reviewCreateForm);
    	model.addAttribute("main", "items/create_review::main");
    	return "layout/logged_in";
    }
    
    @GetMapping("/detail/{id}")    
    public String detail(
        @AuthenticationPrincipal(expression = "user") User user,
        @PathVariable("id")  Long id,
        Model model
    ) {
    	Item item = itemService.findById(id).orElseThrow();
        model.addAttribute("item", item);
        model.addAttribute("user", user);
        model.addAttribute("title", "商品の詳細");
        model.addAttribute("main", "items/detail::main");
        
        //店を取得
        Long shopId = item.getShopId();
        Shop shop = shopService.findById(shopId);
        model.addAttribute("shop", shop);

        // レビューを取得
        List<Review> reviews = reviewService.findByItemIdOrderByCreatedAtDesc(id);
        model.addAttribute("reviews", reviews);
        
        return "layout/logged_in";    
    }
    
    
    
    @GetMapping("/detail/{id}/created_review")
    public String created_review(
    	@AuthenticationPrincipal(expression = "user") User user,
    	@PathVariable("id")  Long id,
    	Model model
	) {
    	Item item = itemService.findById(id).orElseThrow();
    	model.addAttribute("item", item);
    	model.addAttribute("user", user);
    	
    	
    	model.addAttribute("main", "items/created_review::main");
    	return "layout/logged_in";
    }
 
    @GetMapping("/edit/{id}")    
    public String edit(
        @AuthenticationPrincipal(expression = "user") User user,
        @ModelAttribute("itemEditForm") ItemEditForm itemEditForm,
        @PathVariable("id")  Integer id,
        Model model) {
        Item item = itemService.findById(id).orElseThrow();
        itemEditForm.setName(item.getName());
        itemEditForm.setPrice(item.getPrice());
        itemEditForm.setStock(item.getStock());
        itemEditForm.setDescription(item.getDescription());
        model.addAttribute("item", item);
        model.addAttribute("user", user);
        model.addAttribute("title", "投稿の編集");
        model.addAttribute("main", "items/edit::main");
        return "layout/logged_in";    
    }
    
    @PostMapping("/update/{id}")    
    public String update(
        @AuthenticationPrincipal(expression = "user") User user,
        @PathVariable("id")  Integer id,
        @Valid ItemEditForm itemEditForm,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult,
        Model model) {
        if(bindingResult.hasErrors()){
            return edit(user, itemEditForm, id, model);
        }
        itemService.updateItem(
        		id,
                itemEditForm.getName(),
                itemEditForm.getPrice(),
                itemEditForm.getStock(),
                itemEditForm.getDescription(),
                itemEditForm.getImage(),
                itemEditForm.getImg_1(),
                itemEditForm.getImg_2(),
                itemEditForm.getImg_3()
        );  
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品情報の更新が完了しました");
        return "redirect:/admin";
    }
    
    @PostMapping("/delete/{id}")    
    public String delete(
        @PathVariable("id")  Integer id,
        RedirectAttributes redirectAttributes,
        Model model) {
        itemService.delete(id);
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品の削除が完了しました");
        return "redirect:/admin";  
    }
    
    @GetMapping("/search")    
    public String search(
        @AuthenticationPrincipal(expression = "user") User user,
        @RequestParam("keyword") String keyword,
        Model model
    ) {
        List<Item> items = itemService.findByNameContaining(keyword);
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        model.addAttribute("title", "商品検索結果");
        model.addAttribute("main", "items/index::main");
        return "layout/logged_in";
    }
    
    @PostMapping("/toggleLike/{id}")
    public String toggleLike(
    	@PathVariable("id") Integer id,
    	@AuthenticationPrincipal(expression = "user") User user,
    	Model model) {
    	itemService.toggleLike(
    		user,
    		id
    	);
    	return "redirect:/items/detail/{id}";
    }
    
}