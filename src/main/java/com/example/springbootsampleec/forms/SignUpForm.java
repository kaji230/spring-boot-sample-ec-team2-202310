package com.example.springbootsampleec.forms;
 
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String user_name;//name→user_name
    @NotBlank
    private String phone_number;//追加
    @NotBlank
    private String full_name;//追加
    @NotBlank
    private String user_address;//追加 
}