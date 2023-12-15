package com.example.springbootsampleec.forms;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.example.springbootsampleec.entities.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountForm {
	@NotNull
    private int amount;
	
	//選択された商品数を取得する
    public int getAmountSize() {
        return amount;
    }

}
