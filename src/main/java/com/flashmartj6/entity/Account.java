package com.flashmartj6.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "Account")
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

    @Id
    @NotBlank(message = "Không bỏ trống tên")
    private String Username;

    @Nationalized
    @NotBlank(message = "Không bỏ trống tên")
    private String Fullname;

    @NotBlank(message = "Không bỏ trống password")
    private String Password;

    @NotNull(message = "không bỏ trống số điện thoại")
    private Integer Phone;

    @NotBlank(message = "Không bỏ trống Email")
    @Email(message = "Email không đúng định dạng")
    private String Email;
    
    @NotNull(message = "Không bỏ trống giới tính")
    private Boolean Gender;

    @NotNull(message = "Không bỏ trống active")
    private Boolean Active;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Authority> authorities;
    
    public Account(String username) {
        this.Username = username;
    }

}
