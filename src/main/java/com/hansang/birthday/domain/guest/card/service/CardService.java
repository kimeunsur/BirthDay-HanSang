package com.hansang.birthday.domain.guest.card.service;

import com.hansang.birthday.domain.birthday.domain.Birthday;
import com.hansang.birthday.domain.birthday.domain.repository.BirthdayRepository;
import com.hansang.birthday.domain.guest.card.domain.BirthdayCard;
import com.hansang.birthday.domain.guest.card.dto.CardRequest;
import com.hansang.birthday.domain.guest.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final BirthdayRepository birthdayRepository;

    public void registerCard(Long birthdayId,CardRequest cardRequest) {
        Birthday userBirthdayTable = birthdayRepository.findById(birthdayId).orElseThrow();
        BirthdayCard birthdayCard = new BirthdayCard(cardRequest, userBirthdayTable);
        cardRepository.save(birthdayCard);
    }
}
