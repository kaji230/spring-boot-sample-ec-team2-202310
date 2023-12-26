package com.example.springbootsampleec.forms;
 
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEditForm {
	@NotNull
    private String comment;
	
	@NotNull
    private String star;
	
	
	}
	
