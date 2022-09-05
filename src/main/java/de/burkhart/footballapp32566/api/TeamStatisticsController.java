package de.burkhart.footballapp32566.api;

import de.burkhart.footballapp32566.service.TeamStatistics;
import de.burkhart.footballapp32566.service.TeamStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API-Klasse zum Auruf von Methoden zum Anzeigen der Statistik
 * API-Layer
 *
 * @author André Burkhart, 32566
 *
 */

@RestController
@RequestMapping("api/stats")
public class TeamStatisticsController {

    private final TeamStatsService service;

    /**
     * Konstruktor für den TeamStatisticsController
     *
     * @param service
     *                  Den TeamSatsService
     */
    @Autowired
    public TeamStatisticsController(TeamStatsService service) {
        this.service = service;
    }

    /**
     * Gibt ein Ranking des aktuellen Tabellenstandes wieder
     *
     * @return List<TeamStatistics>
     *                  Die geordnete Liste aller Mannschaften
     */
    @GetMapping ("/rank")
    public List<TeamStatistics> getRanking(){

        return service.getRanking();
    }


    /**
     * Gibt eine ungeordnete Liste der Teamstats zurück
     *
     * @return List<TeamStatistics>
     *                  Die ungeordnete Liste aller Mannschaften
     */
    @GetMapping ("/all")
    public List<TeamStatistics> getAllStats(){

        return service.getAllStats();
    }

    /**
     * Gibt den Tabellenführerer zurück
     *
     * @return TeamStatistics
     *                  den Tabellenführer
     */
    @GetMapping ("/first")
    public TeamStatistics getFirst(){

        return service.getBestTeam();
    }

    /**
     * Gibt den Tabellenletzten zurück
     *
     * @return TeamStatistics
     *                  den Tabellenletzten
     */
    @GetMapping ("/last")
    public TeamStatistics getLast(){

        return service.getWorstTeam();
    }

    /**
     * Gibt die Statistik des Teams nach ID zurück
     *
     * @param id
     *                  ID des Teams
     *
     * @return TeamStatistics
     *                  Statistik des angewählten Teams
     */
    @GetMapping ("/{id}")
    public TeamStatistics getStatsByID(@PathVariable Long id){

        return service.getTeamStatsById(id);
    }
}
