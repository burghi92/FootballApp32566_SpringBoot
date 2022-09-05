package de.burkhart.footballapp32566.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * Geschäftsobjekt TeamStatistics zum Anlegen der TeamStatistik eines Teams
 *
 * @author André Burkhart, 32566
 *
 */

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //Necessary in order to handle the Lazy Object
@Data
@Entity
@Table
public class TeamStatistics {

    @Id
    @SequenceGenerator(
            name = "rank_sequence",
            sequenceName = "rank_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rank_sequence"
    )
    private Long id;

    @NotNull
    @NotBlank
    private String teamName;

    @Min(0) @Max(17)
    private int games;

    @Min(0)
    private int goals;

    @Min(0)
    private int goalsAgainst;

    private int goalDifference;

    @Transient
    private int rank;

    @Min(0)
    private int points;

    public TeamStatistics() {
    }

    /**
     * Konstruktor für das Erzeugen einer TeamStatisitk
     *
     * @param team
     *                  Name des Teams
     * @param games
     *                  Anzahl Spiele des Teams
     * @param goals
     *                  Erzielte eigene Tore
     * @param goalsAgainst
     *                  Kassierte Gegentore
     * @param points
     *                  Gewonnene Punkte
     */
    public TeamStatistics(String team, int games, int goals, int goalsAgainst, int points) {
        this.teamName = team;
        this.games = games;
        this.goals = goals;
        this.goalDifference = goals-goalsAgainst;
        this.goalsAgainst = goalsAgainst;
        this.points = points;
    }

}
