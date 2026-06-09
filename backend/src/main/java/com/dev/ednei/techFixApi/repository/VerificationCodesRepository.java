package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.VerificationCodes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodesRepository extends JpaRepository<VerificationCodes, Long> {
}
