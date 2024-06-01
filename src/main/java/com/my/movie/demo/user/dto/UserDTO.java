package com.my.movie.demo.user.dto;

import com.my.movie.demo.user.domain.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserDTO {

    private Long uNum;
    private String email;
    private String username;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String indate;
    private String deldate;
}
