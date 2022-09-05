package de.burkhart.footballapp32566.service;
import de.burkhart.footballapp32566.data.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service-Klasse zum Auruf von Methoden zum Anlegen, Bearbeiten und Löschen von Matches
 * Business-Layer
 *
 * @author André Burkhart, 32566
 *
 */

@Service
public class MatchService {

    MatchRepository repository;
    TeamStatsService stats;


    /**
     * Konstruktor für einen Service zum Umgang mit Matches
     *
     * @param repository
     *                  Das Matchrepository der Matches
     * @param stats
     *                  Den TeamStatsService um die Teamstatistik anzupassen
     */
    @Autowired
    public MatchService(MatchRepository repository, TeamStatsService stats) {
        this.repository = repository;
        this.stats = stats;
    }

    /**
     * Gibt eine Liste von Matches zurück
     *
     * @return List<Matches>
     *                  Eine Liste aller Matches
     */
    public List<Matches> getAllMatches() {
        return repository.findAll();
    }

    /**
     * Gibt ein einzelnes Match zurück
     *
     * @param id
     *                  Die ID des Matches
     * @return Matches
     *                  Ein einzelnes Match
     */
    public Matches getMatchById(Long id) {
        if(!repository.existsById(id)){
            throw new IllegalStateException("Match with id "+ id +" could not be found");
        }
        Matches byId = repository.getById(id);
        return byId;
    }

    /**
     * Fügt ein Match dem Repository hinzu und fügt das Match zu den TeamStats hinzu
     *
     * @param match
     *                  Das Match
     */
    public void addNewMatch(Matches match){
        if (matchInDatabase(match)){
            throw new IllegalStateException("Match already included");
        }

        stats.addMatchStatistic(match);

        repository.save(match);
    }

    /**
     * Fügt eine Liste von Matches dem Repository hinzu und fügt diese Matches zu den TeamStats hinzu
     *
     * @param matches
     *                  Die Liste der matches
     */
    public void addNewMatches(List<Matches> matches){
        for (Matches match : matches) {
            addNewMatch(match);
        }
    }

    /**
     * Löscht ein Match wieder aus dem Repository und korrigiert auch die TeamStats wieder
     *
     * @param match
     *                  Das Match
     */
    public void deleteMatch(Matches match){

        if(!matchInDatabase(match)){
            throw new IllegalStateException("Match could not be found");
        }

        stats.removeMatchStatistic(match);
        repository.delete(match);

    }

    /**
     * Löscht ein Match wieder aus dem Repository und korrigiert auch die TeamStats wieder
     *
     * @param id
     *                  Die ID des Match
     */
    public void deleteMatchbyId(Long id){

        if(!repository.existsById(id)){
            throw new IllegalStateException("Match with id "+ id +" could not be found");
        }
        stats.removeMatchStatistic(getMatchById(id));

        repository.deleteById(id);

    }


    /**
     * Aktualisiert ein Match bezüglich seiner Tore d korrigiert auch die TeamStats wieder
     *
     * @param matchid
     *                  Die ID des Match
     * @param teamGoals1
     *                  Die neuen teamGoals des Heimteams
     * @param teamGoals2
     *                  Die neuen teamGoals des Auswärtsteams
     */
    @Transactional //Mit dieser Annotation geht die Entity in einen "managed state". SQL Abfragen sind nicht notwendig
    public void updateMatchGoals(Long matchid, int teamGoals1, int teamGoals2){
        Matches match = repository.getById(matchid);

        stats.removeMatchStatistic(match);

        if(match.equals(null)){
            throw new IllegalStateException("Match with id "+ matchid +" could not be found");
        }

        if(teamGoals1<0||teamGoals2<0){
            throw new IllegalStateException("Team Goals can not be negativ");
        }
        if(teamGoals1!=match.getTeam1Goals()){
            match.setTeam1Goals(teamGoals1);
        }

        if(teamGoals2!=match.getTeam2Goals()){
            match.setTeam2Goals(teamGoals2);
        }

        stats.addMatchStatistic(match);

    }

    private boolean matchInDatabase(Matches match){
        Optional<Matches> matchInDatabase = repository.findMatchesByTeam1AndTeam2(match.getTeam1(),match.getTeam2());
        return matchInDatabase.isPresent();
    }
}
