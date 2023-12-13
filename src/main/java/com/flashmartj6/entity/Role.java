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
@Table(name = "Role")
public class Role implements Serializable {
    @Id
     private String id;

    @Nationalized
    @NotBlank(message = "Không bỏ RoleName")
    private String RoleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    List<Authority> authority;
    
    
    
    
    
}
