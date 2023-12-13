package com.flashmartj6.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "Category")
@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nationalized
    @NotBlank(message = "Không bỏ trống CategoryName")
    private String CategoryName;
  

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Product> products;
}