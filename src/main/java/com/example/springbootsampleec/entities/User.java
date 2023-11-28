package com.example.springbootsampleec.entities;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity


//userテーブルのカラム設定
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
    
  //@ColumnはDBとJavaフィールドのカラムをマッピングしてくれるアノテーション
    //このアノテーションを付けない場合フィールド名そのままマッピングされる。
 
    @Column(name = "name", length = 60, nullable = false)
    private String name; // ユーザー名
 
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email; // メールアドレス
 
    @Column(name = "password", length = 100, nullable = false)
    private String password;  // パスワード
 
    @Column(name = "roles", length = 120)
    private String roles; // ロール（役割）
 
    @Column(name = "enable_flag", nullable = false)
    private Boolean enable; // 有効フラグ
}