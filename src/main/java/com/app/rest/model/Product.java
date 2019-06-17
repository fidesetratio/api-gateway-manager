package com.app.rest.model;

import java.math.BigDecimal;

public class Product {
	private Integer id;
    private String name;
   
    private BigDecimal price;
   
    private String productId;
       
    private Integer version;
 
    public Product() {
    	super();
    }
   
    public Product(String name, BigDecimal price, String productId, Integer version) {
    super();
        this.name = name;
        this.price = price;
        this.productId = productId;
        this.version = version;
    }
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public BigDecimal getPrice() {
        return price;
    }
 
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
 
    public String getProductId() {
        return productId;
    }
 
    public void setProductId(String productId) {
        this.productId = productId;
    }
 
    public Integer getVersion() {
        return version;
    }
 
    public void setVersion(Integer version) {
        this.version = version;
    }    
}
