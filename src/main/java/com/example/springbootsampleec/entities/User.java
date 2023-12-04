package com.example.springbootsampleec.entities;
 
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
    
    //ManyToMany, JoinTableを追記
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="likes",
    	joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
    	inverseJoinColumns = @JoinColumn(name="item_id", referencedColumnName="id"))
    private Set<Item> likeItems = new HashSet<Item>();
 
    @Column(name = "user_name", length = 60, nullable = false)//name→user_name
    private String user_name; // ユーザー名
 
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email; // メールアドレス
 
    @Column(name = "password", length = 100, nullable = false)
    private String password;  // パスワード
    
    @Column(name = "phone_number", length = 13, nullable = false)//追加
    private String phone_number; //電話番号
    
    @Column(name = "full_name", length = 30, nullable = false)//追加
    private String full_name; //氏名
    
    @Column(name = "user_address", length = 100, nullable = false)//追加
    private String user_address; //住所
    
    //作成日時
    @Column(name="create_date", nullable = false, updatable = false, insertable = false,//追加
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime create_date;
    
    //更新日時
    @Column(name="update_date", nullable = false, updatable = false, insertable = false,//追加
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime update_date;
 
    @Column(name = "roles", length = 120)
    private String roles; // ロール（役割）
 
    @Column(name = "enable_flag", nullable = false)
    private Boolean enable; // 有効フラグ
}