package com.hansang.birthday.domain.guest.card.repository;

import com.hansang.birthday.domain.guest.card.domain.BirthdayCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<BirthdayCard, Long> {
}
