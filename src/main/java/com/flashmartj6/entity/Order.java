package com.flashmartj6.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "AccountId")
	Account account;

	@Temporal(TemporalType.DATE)
	@Column(name = "orderDate")
	private Date orderDate = new Date();

	@Nationalized
	private String Address;

	@Nationalized
	private String Status;

	 @JsonIgnore 
//	@JsonManagedReference
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails;

}
