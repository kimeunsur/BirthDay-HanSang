package com.hansang.birthday.domain.birthday.controller;

import com.hansang.birthday.domain.auth.userdetail.CustomUserDetails;
import com.hansang.birthday.domain.birthday.dto.response.BirthdayResponse;
import com.hansang.birthday.domain.birthday.service.BirthdayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-user/birthday")
@Tag(name = "Birthday")
public class BirthdayController {
    private final BirthdayService birthdayService;

    @Operation(summary = "생일상 생성 API")
    @PostMapping("/create")
    public ResponseEntity<BirthdayResponse> createBirthday(
            @AuthenticationPrincipal CustomUserDetails user) {
        BirthdayResponse response = birthdayService.createBirthday(user.getUserId());
        return ResponseEntity.ok(response);
    }


}
