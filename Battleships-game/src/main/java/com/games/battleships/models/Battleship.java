package com.games.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "battleships")
@Entity
public class Battleship {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ship_id", nullable = false, unique = true, updatable = false)
    private String id;

    @Column(name = "ship_size")
    private Integer size;
}
