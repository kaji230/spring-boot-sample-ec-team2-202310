package com.example.springbootsampleec.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountForm {
	@NotNull
	Item item;
	public int getItemStock() {
		return item.getStock();
	}
}
