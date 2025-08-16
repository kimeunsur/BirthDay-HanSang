package com.hansang.birthday.domain.birthday.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class BirthdayResponse {
    private Long userId;
    private Long birthdayId;

    public static BirthdayResponse of (Long userId, Long birthdayId) {
        return BirthdayResponse.builder()
                .userId(userId)
                .birthdayId(birthdayId)
                .build();
    }
}
