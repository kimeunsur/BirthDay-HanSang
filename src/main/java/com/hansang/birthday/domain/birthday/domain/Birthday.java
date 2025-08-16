package com.hansang.birthday.domain.birthday.domain;

import com.hansang.birthday.domain.quiz.domain.repository.Quiz;
import com.hansang.birthday.domain.user.domain.User;
import com.hansang.birthday.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Birthday extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;
    @Column(nullable = false)
    private Integer year;
    @Column(name = "brand_gift_entry")
    private Boolean brandGiftEntry;
    @Column(name = "quiz_entry")
    private Boolean quizEntry;
    @Column(name = "is_open")
    private Boolean isOpen;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Quiz quiz;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Birthday (Boolean brandGiftEntry, Integer year, Boolean quizEntry, Boolean isOpen, User user) {
        this.brandGiftEntry = brandGiftEntry == null ? false : brandGiftEntry;
        this.year = year;
        this.quizEntry = quizEntry ==null ? false : quizEntry;
        this.isOpen = isOpen == null ? false : isOpen;
        this.user = user;
    }
    public static Birthday of (Integer year, User user) {
        return new Birthday(null, year, null, null, user);
    }
    public void setUser(User user) {
        this.user = user;
    }
}
