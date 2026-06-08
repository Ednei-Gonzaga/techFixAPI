package com.dev.ednei.techFixApi.DTOS.user;

import com.dev.ednei.techFixApi.model.User;

public record UserResumeDTO(
        Long id,
        String name,
        String login,
        String role,
        boolean status
) {
    public UserResumeDTO(User user) {
        this(user.getId(), user.getName(), user.getLogin(), user.getRole().name(), user.getStatus());
    }
}
