package com.example.mobileapp.model;

public class OrderDetails {

    private long id;
    private String quantity;
    private String price;
    private boolean status;

    private long productId;
    private Product productDTO;

    private long orderId;
    private Orders orderDTO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Product getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(Product productDTO) {
        this.productDTO = productDTO;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Orders getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(Orders orderDTO) {
        this.orderDTO = orderDTO;
    }
}
