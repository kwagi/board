package com.example.board.member.entity;

import com.example.board.member.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public final class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long          memberId;
    private String        email;
    private String        password;
    private String        name;
    @Enumerated(value = EnumType.STRING)
    private MemberStatus  memberStatus;
    private LocalDateTime regDate;
    private LocalDateTime deleteDate;
    private LocalDateTime recentDate;
}
