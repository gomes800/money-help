package com.gom.money_help.dto;

import com.gom.money_help.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
