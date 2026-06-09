package com.dev.ednei.techFixApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes")
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String status;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
