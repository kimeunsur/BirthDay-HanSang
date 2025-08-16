package com.hansang.birthday.domain.birthday.service;

import com.hansang.birthday.domain.birthday.domain.Birthday;
import com.hansang.birthday.domain.birthday.domain.repository.BirthdayRepository;
import com.hansang.birthday.domain.birthday.dto.response.BirthdayResponse;
import com.hansang.birthday.domain.birthday.exception.BirthdayException;
import com.hansang.birthday.domain.user.domain.User;
import com.hansang.birthday.domain.user.exception.UserException;
import com.hansang.birthday.domain.user.repository.UserRepository;
import com.hansang.birthday.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
@RequiredArgsConstructor
public class BirthdayService {
    private final BirthdayRepository birthdayRepository;
    private final UserRepository userRepository;

    public BirthdayResponse createBirthday(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        int year = LocalDate.now().getYear();
        if (birthdayRepository.existsByUserAndYear(user, year)) {
            throw new BirthdayException(ErrorCode.BIRTHDAY_ALREADY_EXISTS);
        }
        Birthday birthday = Birthday.of(year, user);
        birthdayRepository.save(birthday);
        return BirthdayResponse.of(user.getUserId(), birthday.getId());
    }
}
