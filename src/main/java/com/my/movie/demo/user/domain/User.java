package com.my.movie.demo.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동으로 생성
@Table(name = "User")
public class User extends  TimeEntity{

    @Id
    private String email;
    private String username;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String indate;
    private String deldate;

    public void modify(String nickname, String password){
        this.nickname=nickname;
        this.password=password;
    }

    public User updateModifiedDate(){
        this.onPreUpdate();
        return this;
    }

    public String getRoleValue(){
        return this.role.getValue();
    }
}
