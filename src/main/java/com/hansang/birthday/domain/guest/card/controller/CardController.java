package com.hansang.birthday.domain.guest.card.controller;

import com.hansang.birthday.domain.guest.card.dto.CardRequest;
import com.hansang.birthday.domain.guest.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-guest/card/{birthdayId}")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<Void> addCard(@PathVariable("birthdayId") Long birthdayId,
                                        @RequestBody CardRequest cardRequest) {
        cardService.registerCard(birthdayId,cardRequest);
        return ResponseEntity.ok().build();

    }


}
