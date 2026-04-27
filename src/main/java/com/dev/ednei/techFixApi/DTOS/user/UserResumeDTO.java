package com.dev.ednei.techFixApi.DTOS.user;

import com.dev.ednei.techFixApi.model.User;

public record UserResumeDTO(
        String name,
        String login,
        String role
) {
    public UserResumeDTO(User user) {
        this(user.getName(), user.getLogin(), user.getRole().name());
    }
}
