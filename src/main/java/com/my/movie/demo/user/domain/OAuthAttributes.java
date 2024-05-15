package com.my.movie.demo.user.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String nickname;
    private String email;
    private Role role;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofNaver("email", attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response=(Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .username((String) response.get("username"))
                .email((String) response.get("email"))
                .nickname((String) response.get("nickname"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .username(username)
                .email(email)
                .nickname(nickname)
                .role(Role.SOCIAL)
                .build();
    }
}
