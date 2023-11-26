package com.example.springbootsampleec.entities;
 
import java.time.ZonedDateTime;

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
@Table(name = "item")
@Entity


//itemテーブルのカラム設定
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
 
    @Column(name = "name", length = 200, nullable = false)
    private String name; // 商品名
    
    @Column(name = "price", nullable = false)
    private int price; // 金額
    
    @Column(name = "stock", nullable = false)
    private int stock; // 在庫数
    
    @Column(name = "description", length = 1000, nullable = false)
    private String description; // 商品説明
 
    @Column(name = "image", length = 100, nullable = false)
    private String image;  // 画像
    
    // 作成日時
    @Column(name="createAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime createdAt;
 
    // 更新日時
    @Column(name="updateAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime updatedAt;
 
}