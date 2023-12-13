package com.flashmartj6.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Nationalized
	@NotBlank(message = "Không bỏ trống tên")
	private String ProductName;

	@NotNull(message = "Không bỏ trống giá")
	@Min(value = 1, message = "Giá lớn hơn 0")
	private Float Price;
	
	@NotNull(message = "Không bỏ trống số lượng")
	@Min(value = 1, message = "Số lượng lớn hơn 0")
	private Integer Quantity;

	private String Image;

	@Nationalized
	@NotBlank(message = "Không bỏ trống thương hiệu")
	private String Brand;

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	Category category;

	 @JsonIgnore 
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<ProductDetail> productDetails;
	

}
