package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.VerificationCodes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodesRepository extends JpaRepository<VerificationCodes, Long> {
    Optional<VerificationCodes> findByCode(String code);
}
