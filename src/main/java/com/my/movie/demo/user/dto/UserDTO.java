package com.my.movie.demo.user.dto;

import com.my.movie.demo.user.domain.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String email;
    private String username;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String indate;
    private String deldate;
}
