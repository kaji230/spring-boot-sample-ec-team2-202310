package com.example.springbootsampleec.entities;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ShoppingCart {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "Shopping_id")
	  private Long id;

	  @ManyToOne(fetch=FetchType.LAZY)
	  @JoinColumn(name = "user_id")
	  private User user;

	  @ManyToOne(fetch=FetchType.LAZY)
	  @JoinColumn(name = "item_id")
	  private Item item;

	  @Column(name = "amount")
	  private int orderAmount;

	    @Column(name="create_date", nullable = false, updatable = false, insertable = false,//追加
	    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	    private ZonedDateTime create_date;

	    
	    @Column(name="update_date", nullable = false, updatable = false, insertable = false,//追加
	    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	    private ZonedDateTime update_date;
}
