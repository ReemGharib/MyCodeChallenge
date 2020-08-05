package com.retail.discount.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Builder
@Data
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="total_price")
    private double totalPrice;
    @Column(name="price_after_discount")
    private String priceAfterDiscount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;





}
