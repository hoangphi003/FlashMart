package com.flashmartj6.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "Color")
public class Color implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Nationalized
	@NotBlank(message = "Không bỏ trống ColorName")
	private String ColorName;

	@JsonIgnore
	@OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
	List<ProductDetail> productDetails;
	
}
