package com.flashmartj6.entity;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Cart")
@Data
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne @JoinColumn(name = "AccountId")
    Account account;

    @JsonIgnore
    @ManyToOne @JoinColumn(name = "ProductDetailId")
    ProductDetail productDetail;

    private Integer Quantity;
}
