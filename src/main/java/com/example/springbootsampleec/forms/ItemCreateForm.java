package com.example.springbootsampleec.forms;
 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.springbootsampleec.entities.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateForm {
	@NotNull
	private Shop selectedShop;
	
    @NotNull
    @Size(min=1, max=200)
    private String name;
    
    @NotNull
    private int price;
    
    @NotNull
    private int stock;
    
    @NotNull
    private int type;
    
    @NotNull
    @Size(min=1, max=1000)
    private String description;
    
    @NotNull
    private  MultipartFile image;
    
    private  MultipartFile img_1;
    
    private  MultipartFile img_2;
    
    private  MultipartFile img_3;
    
 // selectedShopを取得するメソッド
    public Shop getSelectedShop() {
        return selectedShop;
    }
    
    
}