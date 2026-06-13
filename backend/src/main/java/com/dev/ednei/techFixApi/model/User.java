package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.model.enums.RoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    private boolean status;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at")
    private  LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    @OneToMany(mappedBy = "userTechnical")
    private List<ServiceOrder> serviceOrder;

    @OneToMany(mappedBy = "user")
    private List<ServiceOrderTask> serviceOrderTasks;

    @OneToMany(mappedBy = "user")
    private List<ServiceOrderHistory> serviceOrderHistory;

    @OneToMany(mappedBy = "user")
    private List<VerificationCodes> verificationCodes;

    @OneToMany(mappedBy = "user")
    private List<PaymentsHistory> paymentsHistory;

    public User(UserCreateDTO userCreateDTO, String password) {
        this.login = userCreateDTO.cpf();
        this.password = password;
        this.status = true;
        this.createdAt = LocalDateTime.now();
        this.role = RoleUser.forValue(userCreateDTO.role());
    }

    public User(Long idUser) {
        this.id = idUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        for(RoleUser r : RoleUser.values()){
            roles.add(new SimpleGrantedAuthority("ROLE_" + r.name()));
        }

        return roles;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
