package com.example.board.member.entity;

import com.example.board.member.enums.Status;
import com.example.board.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Long          id;
    private String        email;
    private String        password;
    private String        name;
    @Enumerated(value = EnumType.STRING)
    private Status        status;
    private LocalDateTime regDate;
    private LocalDateTime deleteDate;
    private LocalDateTime recentDate;
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Post>    posts = new ArrayList<>();
}