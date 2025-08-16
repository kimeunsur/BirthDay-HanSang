package com.hansang.birthday.domain.guest.card.domain;

import com.hansang.birthday.domain.birthday.domain.Birthday;
import com.hansang.birthday.domain.guest.card.dto.CardRequest;
import com.hansang.birthday.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="birthday_card")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BirthdayCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long birthdayCardId;

    private String nickname;

    private String messageText;

    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "birthday_id")
    private Birthday birthday;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public BirthdayCard(CardRequest cardRequest,Birthday birthday) {
        this.nickname = cardRequest.getNickname();
        this.messageText = cardRequest.getMessageText();
        this.imageName = cardRequest.getImageName();
        this.birthday = birthday;
    }
}
