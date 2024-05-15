package com.my.movie.demo.user.dto;

import com.my.movie.demo.user.domain.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserSessionDTO {
    private String email;
    private String username;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String indate;
    private String deldate;
}
