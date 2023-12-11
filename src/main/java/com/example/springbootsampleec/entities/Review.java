package com.example.springbootsampleec.entities;
 
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "review")
@Entity


//reviewテーブルのカラム設定
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // レビューid
    
    @ManyToOne
    private Item item; // 商品id
    
    @ManyToOne
    private User user; // 商品id
    
    //コメント、評価を追加する
    @Column(name = "comment", length = 1000, nullable = true)
    private String comment; //レビュー本文
    
    @Column(name = "star", length = 1000, nullable = false)
    private int star; //星の数
   
    @Column(name="createdAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime createdAt;   // 登録日時
 
    @Column(name="updatedAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime updatedAt;   // 更新日時
    
    public String getUserName() {
    	return this.user.getName();
    }
    
}