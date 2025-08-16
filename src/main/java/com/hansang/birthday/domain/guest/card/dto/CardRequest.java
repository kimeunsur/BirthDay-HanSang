package com.hansang.birthday.domain.guest.card.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequest {
    private String nickname;
    private String messageText;
    private String imageName;
}
