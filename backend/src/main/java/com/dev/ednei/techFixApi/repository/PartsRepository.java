package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRepository extends JpaRepository<Parts, Long> {
}
