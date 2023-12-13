package com.flashmartj6.entity;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "ProductDetail")
public class ProductDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    Product product;

    @ManyToOne
    @JoinColumn(name = "SizeId")
    Size size;

    @ManyToOne
    @JoinColumn(name = "ColorId")
    Color color;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail")
    List<Cart> carts;

}
