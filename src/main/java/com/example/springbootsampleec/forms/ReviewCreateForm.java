package com.example.springbootsampleec.forms;
 
import javax.validation.constraints.NotNull;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateForm {
    private String comment;
	
    private String star;
	
	@NotNull
	Item item;
	public Long getItemId() {
		return item.getId();
	}
	
	
	@NotNull
	User user;
	public Long getUserId() {
		return user.getId();
	}
	
}