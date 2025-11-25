package com.library.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    @Id
    @Column(name = "user_id")
    private Long userId;  // shared primary key

    private String position;

    @OneToOne
    @MapsId            // dùng chung PK với User.id
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
