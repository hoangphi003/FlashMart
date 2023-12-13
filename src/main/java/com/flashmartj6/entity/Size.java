package com.flashmartj6.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Size")
@Data
public class Size implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "Không bỏ trống SizeName")
	private String SizeName;

	@JsonIgnore
	@OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
	List<ProductDetail> productDetails;
}
