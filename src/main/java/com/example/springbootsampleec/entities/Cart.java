package com.example.springbootsampleec.entities;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "cart")
@Entity

//cartのカラム設定
public class Cart {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
	
	// userエンティティとの関連を追加
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;//ユーザーid
    
    // Itemエンティティとの関連を追加
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;//商品id
    
    //購入予定商品数
    @Column(name = "amount", nullable = true)
    private int amount;//注文数
    
    
    // 登録日時
    @Column(name="createdAt",nullable = false, updatable = false, insertable = false, 
    	    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    	    private ZonedDateTime createdAt;
    
    // 更新日時 
    @Column(name="updatedAt",nullable = false, updatable = false, insertable = false, 
    		columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    	    private ZonedDateTime updatedAt;
    
    
	
}
