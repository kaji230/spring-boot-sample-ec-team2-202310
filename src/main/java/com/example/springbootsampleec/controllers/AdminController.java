package com.example.springbootsampleec.controllers;
 
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.services.ItemService;
 
@Controller
public class AdminController { 
    private final ItemService itemService;
    
    public AdminController(
        ItemService itemService
    ) {
        this.itemService = itemService;
    }
    
    @GetMapping("/admin")    
    public String admin(
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
    ) {
        List<Item> items = itemService.findAllByOrderByCreatedAtDesc();
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        model.addAttribute("title", "管理ページ");
        model.addAttribute("main", "admins/admin::main");
        return "layout/logged_in";
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
}