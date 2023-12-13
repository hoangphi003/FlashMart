package com.flashmartj6.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "OrderDetail")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne @JoinColumn(name = "ProductId")
    Product product;

//    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "OrderId")
    Order order;

    private Integer Quantity;

    private Float Price;

    private Float Total;

}
