package com.games.battleships.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "battleships_games")
public class BattleshipsGame {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "game_id", nullable = false, unique = true, updatable = false)
    private String id;


    @Lob
    @Column(name = "player_field")
    private byte[] playerField;

    @Lob
    @Column(name = "computer_field")
    private byte[] computerField;

    @Lob
    @Column(name = "visual_field")
    private byte[] visualField;
}
