package com.example.springbootsampleec.forms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.repositories.CartRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountForm {
	@Autowired
    private CartRepository cartRepo;
   
    public List<Cart> getAmount() {
        return cartRepo.findAll();
    }

}
