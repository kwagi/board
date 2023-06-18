package com.example.board.member.entity;

import com.example.board.member.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "member")
public final class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long          id;
    private String        email;
    private String        password;
    private String        name;
    @Enumerated(value = EnumType.STRING)
    private Status        status;
    private LocalDateTime regDate;
    private LocalDateTime deleteDate;
    private LocalDateTime recentDate;
}