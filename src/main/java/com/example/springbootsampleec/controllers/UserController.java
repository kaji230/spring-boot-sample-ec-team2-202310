package com.example.springbootsampleec.controllers;
 
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Review;
import com.example.springbootsampleec.entities.Shop;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.forms.SignUpForm;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.ReviewService;
import com.example.springbootsampleec.services.ShopService;
import com.example.springbootsampleec.services.UserService;
 
@RequestMapping("/users")
@Controller
public class UserController { 
    // UserController 内で UserService を利用できるように
    private final UserService userService;
    private final ItemService itemService;
	private final ReviewService reviewService;
	private final ShopService shopService;
    
    public UserController(
    	UserService userService,
    	ItemService itemService,
        ReviewService reviewService,
        ShopService shopService
    ) {
        this.userService = userService;
        this.itemService = itemService;
        this.reviewService = reviewService;
        this.shopService = shopService;
    }
    
    @GetMapping("/sign_up")    
    public String signUp(
        @ModelAttribute("signUpForm") SignUpForm signUpForm,
        Model model
        ) {
        model.addAttribute("title", "サインアップ");
        model.addAttribute("main", "users/sign_up::main");
        return "layout/not_logged_in";    
    }
    
    // サインアップフォーム投稿時の処理を追記
    @PostMapping("/sign_up")
    public String signUpProcess(
        @ModelAttribute("sign_up") SignUpForm signUpForm,
        RedirectAttributes redirectAttributes,
        Model model){
        String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
        userService.register(
        		signUpForm.getUser_name(),//変更
                signUpForm.getEmail(),
                signUpForm.getPassword(),
                signUpForm.getPhone_number(),
                signUpForm.getFull_name(),
                signUpForm.getUser_address(),
                roles);
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "アカウントの登録が完了しました");
        return "redirect:/users/login";
    }
 
    @GetMapping("/login")    
    public String login(Model model) {
        model.addAttribute("title", "ログイン");
        model.addAttribute("main", "users/login::main");
        return "layout/not_logged_in";    
    }
 
    @GetMapping("/detail/{id}")    
    public String detail(
        @AuthenticationPrincipal(expression = "user") User loginUser,
        @PathVariable("id")  Integer id,
        Model model
    ) {
        User user = userService.findById(id).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("title", "お気に入り一覧");
        model.addAttribute("main", "users/detail::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/edit/{id}")    
    public String edit(Model model) {
        model.addAttribute("title", "ユーザー情報を編集");
        model.addAttribute("main", "users/edit::main");
        return "layout/logged_in";    
    }
    
    @GetMapping("/review/{id}")    
    public String review(
        @AuthenticationPrincipal(expression = "user") User user,
        @PathVariable("id")  Long id,
        Model model
    ) {
    	Optional<User> refreshedUser = userService.findById(user.getId());
    	model.addAttribute("user", refreshedUser.orElseThrow());
        model.addAttribute("title", "レビュー一覧");
        model.addAttribute("main", "users/review::main");

        // レビューを取得
        List<Review> reviews = reviewService.findByUserIdOrderByCreatedAtDesc(id);
        model.addAttribute("reviews", reviews);
        
        return "layout/logged_in";    
    }
}