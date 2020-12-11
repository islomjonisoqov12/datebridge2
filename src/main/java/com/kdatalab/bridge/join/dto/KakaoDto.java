package com.kdatalab.bridge.join.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 카카오 회원 정보
 * @author Enclouds
 * @since 2020.12.11
 */
@Data
public class KakaoDto {

    private Map<String, Object> attribute;
    private String registrationId;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public KakaoDto(Map<String, Object> attribute, String nameAttributeKey, String name, String email, String registrationId){
        this.attribute = attribute;
        this.registrationId = registrationId;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static KakaoDto of(String registrationId, String userNameAttributeName, Map<String, Object> attribute){
        return ofkakako(registrationId, "id", attribute);
    }

    private static KakaoDto ofkakako(String registrationId, String userNameAttributeName, Map<String, Object> attribute){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        profile.put("email", kakaoAccount.get("email"));
        profile.put("username", kakaoAccount.get("nickname"));
        profile.put("id", attribute.get("id"));

        return KakaoDto.builder().name((String) profile.get("username"))
                .email((String) profile.get("email"))
                .attribute(profile)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

}
