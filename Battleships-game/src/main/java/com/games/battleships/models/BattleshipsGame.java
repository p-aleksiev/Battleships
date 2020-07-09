package com.games.battleships.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "games")
public class BattleshipsGame {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "game_id", nullable = false, unique = true, updatable = false)
    private String id;


    @Column(name = "player_field")
    private byte[] playerField;

    @Column(name = "computer_field")
    private byte[] computerField;

    @Column(name = "visual_field")
    private byte[] visualField;
}
