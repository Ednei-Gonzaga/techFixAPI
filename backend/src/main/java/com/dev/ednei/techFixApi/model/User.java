package com.dev.ednei.techFixApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    private String login;

    private String password;

    private boolean status;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_date")
    private  LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToOne(mappedBy = "idUser")
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

}
