package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.enums.RoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String Login;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleUser role;

    private Boolean status;

    @OneToMany(mappedBy = "user")
    private List<ServiceOrder> serviceOrderList;
}
