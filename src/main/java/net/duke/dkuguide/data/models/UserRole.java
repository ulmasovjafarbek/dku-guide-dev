package net.duke.dkuguide.data.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRoleEnum name;

}
