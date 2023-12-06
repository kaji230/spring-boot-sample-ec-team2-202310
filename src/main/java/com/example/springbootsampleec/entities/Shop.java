package com.example.springbootsampleec.entities;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "shop")
@Entity

//出品店舗のテーブル設定
public class Shop {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 店舗id
	
	// 1:n の関係を定義（店舗idと商品テーブル）
    @OneToMany(mappedBy="shop", fetch = FetchType.EAGER)
    private List<Item> items;
    
    @Column(name = "shop_account", length = 60, nullable = false, unique = true)
    private String shop_account; // 店舗アカウント名
 
    @Column(name = "shop_name", length = 60, nullable = false)
    private String shop_name; // 店舗名
    
    @Column(name = "shop_address", length = 100, nullable = false)
    private String shop_address; //店舗住所
 
    @Column(name = "shop_icon", length = 100, nullable = true)
    private String shop_icon;  // 店舗アイコンのファイル名
    
    @Column(name = "shop_description", length = 1000, nullable = false)
    private String shop_description; //店舗情報
     
    //作成日時
    @Column(name="create_date", nullable = false, updatable = false, insertable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime create_date;
    
    //更新日時
    @Column(name="update_date", nullable = false, updatable = false, insertable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime update_date;

    public String getName() {
    	return this.shop_name;
    }
    public Long getId() {
    	return this.id;
    }
}
