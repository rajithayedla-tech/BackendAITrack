package com.airtribe.TrustDesk.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {

    private String sku;

    private String name;

    private Integer quantity;

    private String category;

    private boolean finalSale;

    public OrderItem() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFinalSale() {
        return finalSale;
    }

    public void setFinalSale(boolean finalSale) {
        this.finalSale = finalSale;
    }
}