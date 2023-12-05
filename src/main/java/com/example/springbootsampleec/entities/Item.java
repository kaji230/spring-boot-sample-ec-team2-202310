package com.example.springbootsampleec.entities;
 
import java.time.ZonedDateTime;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
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
@Table(name = "item")
@Entity


//itemテーブルのカラム設定
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 商品id
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
	private Shop shop; //店舗id
    
    //@Column(name = "shop_id", nullable = false)
   // private int shop_id; // 店舗id
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="likes",
    	joinColumns = @JoinColumn(name="item_id", referencedColumnName="id"),
    	inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id"))
    private Set<User> likedUsers = new HashSet<User>();
 
    @Column(name = "name", length = 200, nullable = false)
    private String name; // 商品名
    
    @Column(name = "price", nullable = false)
    private int price; // 価格
    
    @Column(name = "stock", nullable = false)
    private int stock; // 在庫数
    
    @Column(name = "type", nullable = false)
    private int type; // 商品の種類
    
    @Column(name = "description", length = 1000, nullable = false)
    private String description; // 商品説明
    
    @Column(name = "main_img", length = 100, nullable = false)
    private String image;  // メイン画像
    
    @Column(name = "img_1", length = 100, nullable = true)
    private String img_1;  // サブ画像1
    
    @Column(name = "img_2", length = 100, nullable = true)
    private String img_2;  // サブ画像2
    
    @Column(name = "img_3", length = 100, nullable = true)
    private String img_3;  // サブ画像3
 
    @Column(name="createdAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime createdAt;   // 登録日時
 
    @Column(name="updatedAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime updatedAt;   // 更新日時
    
    public String getShopName() {
    	return this.shop.getName();
    }
    
}