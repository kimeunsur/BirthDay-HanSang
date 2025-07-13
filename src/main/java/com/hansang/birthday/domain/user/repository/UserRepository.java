package com.hansang.birthday.domain.user.repository;

import com.hansang.birthday.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(long kakaoId);
    Optional<User> findByRefreshToken(String refreshToken);
}
