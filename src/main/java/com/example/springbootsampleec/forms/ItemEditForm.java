package com.example.springbootsampleec.forms;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEditForm {
	@NotNull
    @Size(min=1, max=200)
    private String name;
    
    @NotNull
    @Min(value=1)
    private int price;
    
    @NotNull
    private int stock;
    
    @NotNull
    @Size(min=1, max=1000)
    private String description;
    
    @NotNull
    private  MultipartFile image;
    
    private  MultipartFile img_1;
    
    private  MultipartFile img_2;
    
    private  MultipartFile img_3;
 
 
}