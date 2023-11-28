package com.example.springbootsampleec.services;
 
import com.example.springbootsampleec.entities.User;
 
import java.util.List;
import java.util.Optional;
 
public interface UserService {
    // ユーザー一覧の取得
    List<User> findAll();
    // ユーザーの取得
    Optional<User> findById(long id);
 // ユーザーの登録　変更
    void register(String user_name, String email, String password, String phone_number, String full_name, String user_address, String[] roles);
}