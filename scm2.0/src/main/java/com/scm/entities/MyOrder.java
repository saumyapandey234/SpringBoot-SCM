package com.scm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class MyOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long myOrderId;
  private String orderId;
  private String currency;
  private int amount;
  private String receipt;
  private String status;
  @ManyToOne
  private User user;

  private String paymentId;

  // Getters and Setters
  public Long getMyOrderId() {
    return myOrderId;
  }

  public void setMyOrderId(Long myOrderId) {
    this.myOrderId = myOrderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getReceipt() {
    return receipt;
  }

  public void setReceipt(String receipt) {
    this.receipt = receipt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

}
