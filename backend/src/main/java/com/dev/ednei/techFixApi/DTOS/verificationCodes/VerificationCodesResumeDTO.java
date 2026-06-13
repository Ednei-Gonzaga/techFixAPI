package com.dev.ednei.techFixApi.DTOS.verificationCodes;

import com.dev.ednei.techFixApi.model.VerificationCodes;

import java.time.LocalDateTime;

public record VerificationCodesResumeDTO(
        Long id,
        LocalDateTime createdAt,
        String status,
        LocalDateTime expiredAt,
        Long idUser
) {
    public VerificationCodesResumeDTO(VerificationCodes verificationCode) {
        this(verificationCode.getId(), verificationCode.getCreatedAt(), verificationCode.getStatus().portugueseOption, verificationCode.getExpiredAt(), verificationCode.getUser().getId());
    }
}
