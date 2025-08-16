package com.hansang.birthday.domain.birthday.domain.repository;

import com.hansang.birthday.domain.birthday.domain.Birthday;
import com.hansang.birthday.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthdayRepository extends JpaRepository<Birthday, Long> {
    Boolean existsByUserAndYear(User user, int year);
}
