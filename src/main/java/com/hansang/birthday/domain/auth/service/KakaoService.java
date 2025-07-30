package com.hansang.birthday.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansang.birthday.domain.auth.tokens.AuthTokens;
import com.hansang.birthday.domain.auth.tokens.AuthTokensGenerator;
import com.hansang.birthday.domain.user.domain.User;
import com.hansang.birthday.domain.auth.dto.LoginResponse;
import com.hansang.birthday.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @Value("${KAKAO_CLIENT_ID}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    public LoginResponse kakaoLogin(String code) {
        String accessToken = getAccessToken(code);
        HashMap<String, Object> userInfo = getKakaoUserInfo(accessToken);
        return login(userInfo);
    }

    private String getAccessToken(String code){
        //HTTP header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);

        //HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //HTTP 응답(json) => 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try{
            jsonNode = objectMapper.readTree(responseBody);

        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonNode.get("access_token").asText(); //토큰 전송

    }

    private HashMap<String, Object> getKakaoUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        //HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        //HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );
        //responseBody에 있는 정보 꺼내기
        String responseBody = response.getBody();
        //log.info("responseBody: {}", responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        JsonNode kakaoAccount = jsonNode.get("kakao_account");
        JsonNode profile = kakaoAccount.get("profile");
        Long kakaoId = jsonNode.get("id").asLong();
        String name = profile.get("nickname").asText();
        String profileImageUrl = profile.get("profile_image_url").asText();
        String birthday = kakaoAccount.get("birthday").asText();
        String birthyear = kakaoAccount.get("birthyear").asText();

        LocalDate birthDate = makeBirthDate(birthyear,birthday);

        userInfo.put("kakaoId", kakaoId);
        userInfo.put("name", name);
        userInfo.put("profileImageUrl", profileImageUrl);
        userInfo.put("birthday", birthDate);

        return userInfo;
    }

    private LocalDate makeBirthDate(String birthyear, String birthday) {
        String birthDateStr = birthyear+birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(birthDateStr, formatter);
    }


    private LoginResponse login(HashMap<String, Object> userInfo) {
        User user;
        Long kakaoId = Long.valueOf(userInfo.get("kakaoId").toString());
        String name = userInfo.get("name").toString();
        String profileImageUrl = userInfo.get("profileImageUrl").toString();
        LocalDate birthday = (LocalDate) userInfo.get("birthday");

        Optional<User> findUser = userRepository.findByKakaoId(kakaoId);
        if (findUser.isEmpty()) {
            user = new User(name, profileImageUrl, kakaoId, birthday);
            user= userRepository.save(user);
        }
        else{
            user = findUser.get();
        }
        Long userId = user.getUserId();
        AuthTokens token = authTokensGenerator.generate(userId.toString());
        user.setRefreshToken(token.getRefreshToken());
        userRepository.save(user);
        return new LoginResponse(userId, token);
    }

}
