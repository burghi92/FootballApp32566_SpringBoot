package de.burkhart.footballapp32566.api;

import de.burkhart.footballapp32566.service.MatchService;
import de.burkhart.footballapp32566.service.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API-Klasse zum Auruf von Methoden zum Anlegen, Bearbeiten und Löschen von Matches
 * API-Layer
 *
 * @author André Burkhart, 32566
 *
 */

@RestController
@RequestMapping("api/matches")
public class MatchController {

    private final MatchService service;

    /**
     * Konstruktor für den MatchController
     *
     * @param service
     *                  Den MatchService der Matches
     */
    @Autowired
    public MatchController(MatchService service) {

        this.service = service;
    }

    /**
     * Gibt eine Liste aller Matches zurück
     *
     * @return List<Matches>
     *                  Die Liste der Matches
     */
    @GetMapping
    public List<Matches> getAllMatches(){
        return service.getAllMatches();
    }


    /**
     * Gibt ein einzelnes Match zurück
     *
     * @param id
     *                  Die ID eines Matches
     * @return Matches
     *                  Ein einzelnes Match
     */

    @GetMapping ("/{id}")
    public Matches getMatchByID(@PathVariable Long id){

        Matches match = service.getMatchById(id);

       return match;
    }

    /**
     * Fügt ein neues Match hinzu
     *
     * @param match
     *                  Das Match
     */
    @PostMapping()
    public void addNewMatch(@RequestBody Matches match){
        service.addNewMatch(match);
    }

    /**
     * Fügt eine Liste von Matches hinzu
     *
     * @param matches
     *                  Liste von Matches
     */
    @PostMapping("/matchday")
    public void addNewMatch(@RequestBody List<Matches> matches){

        service.addNewMatches(matches);
    }
    /**
     * Löscht ein Match auf Basis einer ID
     *
     * @param id
     *                  Die ID des Match
     */
    @DeleteMapping(path = "{id}")
    public void deleteMatchById(@PathVariable("id") Long id){

        service.deleteMatchbyId(id);
    }

    /**
     * Löscht ein Match auf Basis eines Matches
     *
     * @param match
     *                  Das zu löschende Match
     */
    @DeleteMapping
    public void deleteMatch(@RequestBody Matches match){

        service.deleteMatch(match);
    }


    /**
     * Aktualisiert die MatchGoals auf Basis der ID
     *
     * @param teamGoals1
     *                  Die Tore von Team1
     * @param teamGoals2
     *                  Die Tore von Team2
     * @param id
     *                  Die ID des Teams
     */
    @PutMapping(path = "{id}")
    public void updateMatchGoals(@PathVariable("id") Long id,
                              @RequestParam(required = false) int teamGoals1,  //Request parameter
                              @RequestParam(required = false) int teamGoals2){
        service.updateMatchGoals(id, teamGoals1, teamGoals2);
    }


}
