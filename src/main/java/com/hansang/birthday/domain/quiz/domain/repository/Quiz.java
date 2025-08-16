package com.hansang.birthday.domain.quiz.domain.repository;

import com.hansang.birthday.domain.birthday.domain.Birthday;
import com.hansang.birthday.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;
    @OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JoinColumn(name = "birthday_id", nullable = false)
    private Birthday birthday;
}
