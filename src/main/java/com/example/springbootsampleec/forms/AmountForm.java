package com.example.springbootsampleec.forms;

import javax.validation.constraints.NotNull;

import com.example.springbootsampleec.entities.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountForm {
	int getAmountSize;
}
