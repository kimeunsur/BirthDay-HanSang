package com.hansang.birthday.domain.birthday.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class BirthdayListResponse {
    private List<BirthdayResponse> response;
}
