package com.dev.ednei.techFixApi.DTOS.supportContacts;

import com.dev.ednei.techFixApi.model.SupportContact;

import java.time.LocalDateTime;

public record SupportContactFullDTO(
        Long id,
        String type,
        String contact,
        String description,
        LocalDateTime createdAt
) {
    public SupportContactFullDTO(SupportContact supportContact) {
        this(supportContact.getId(), supportContact.getType().name(), supportContact.getContact(), supportContact.getDescription(), supportContact.getCreatedAt());
    }
}
