package com.dev.ednei.techFixApi.repository;


import com.dev.ednei.techFixApi.model.SupportContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportContactRepository extends JpaRepository<SupportContact, Long> {

    Boolean existsByContact(String contact);

}
