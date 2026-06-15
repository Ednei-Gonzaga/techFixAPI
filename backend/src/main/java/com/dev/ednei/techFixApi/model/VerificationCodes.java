package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.enums.StatusVerificationCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VerificationCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private StatusVerificationCode status;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public VerificationCodes(String code, User userLogin) {
        this.code = code;
        this.createdAt = LocalDateTime.now();
        this.status = StatusVerificationCode.ACTIVE;
        this.expiredAt = createdAt.plusMinutes(10);
        this.user = userLogin;

    }

    public void updateStatusUsed() {
        this.status = StatusVerificationCode.USED;
    }
}
