package net.duke.dkuguide.data.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tag")
public class ProfessorTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ProfTagEnum tagName;

}
