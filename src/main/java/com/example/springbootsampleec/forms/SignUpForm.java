package com.example.springbootsampleec.forms;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import javax.validation.constraints.NotNull;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String user_name;//name→user_name
    @NotNull
    private String phone_number;//追加
    @NotNull
    private String full_name;//追加
    @NotNull
    private String user_address;//追加 
}