package com.flashmartj6.entity;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;

import com.flashmartj6.entity.Role;

@Entity
@Data
@Table(name = "Authority")
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne @JoinColumn(name = "AccountId")
    Account account;

    @ManyToOne @JoinColumn(name = "RoleId")
    Role role;
}
