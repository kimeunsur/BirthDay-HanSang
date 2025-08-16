package com.hansang.birthday.domain.user.domain;


import com.hansang.birthday.domain.birthday.domain.Birthday;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotEmpty
    private Long kakaoId;

    @NotEmpty
    private LocalDate birthday;

    @Column(length = 512)
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Birthday> birthdays = new ArrayList<>();

    @Builder
    public User(String name, String profileImageUrl, Long kakaoId,LocalDate birthday) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.createdAt = LocalDateTime.now();;
        this.kakaoId = kakaoId;
        this.birthday = birthday;
    }


}
