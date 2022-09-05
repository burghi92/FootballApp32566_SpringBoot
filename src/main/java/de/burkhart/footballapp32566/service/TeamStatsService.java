package de.burkhart.footballapp32566.service;

import de.burkhart.footballapp32566.data.TeamStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Service-Klasse zum Auruf von Methoden zum Anlegen, Bearbeiten und Löschen von TeamStatistics
 * Business-Layer
 *
 * @author André Burkhart, 32566
 *
 */
@Service
public class TeamStatsService {
    TeamStatsRepository repository;


    /**
     * Konstruktor für einen Service zum Umgang mit TeamStatisics
     *
     * @param repository
     *                  Das Matchrepository der Matches
     */
    @Autowired
    public TeamStatsService(TeamStatsRepository repository) {

        this.repository = repository;
    }

    /**
     * Gibt eine nach ihrem Erfolg geordnete Liste (Tabelle) aller Mannschaften zurück
     *
     * @return List<TeamStatistics>
     *                  Eine geordnete Liste aller TeamStatistiken
     */
    public List<TeamStatistics> getRanking(){
        TeamStatistics dummyTeam = new TeamStatistics("Dummy", 0, 0, 3000, 0);
        TeamStatistics selectedTeam = dummyTeam;

        List<TeamStatistics> teams = getAllStats();
        List<TeamStatistics> ranking = new ArrayList<>();
        int listsize = teams.size();
        for (int i = 0; i < listsize; i++) {

            for (TeamStatistics team : teams) {
                if (team.getPoints() > selectedTeam.getPoints()) {
                    selectedTeam = team;
                } else if (team.getPoints() == selectedTeam.getPoints()) {
                    if (team.getGoalDifference() > selectedTeam.getGoalDifference()) {
                        selectedTeam = team;
                    } else if (team.getGoalDifference() == selectedTeam.getGoalDifference() && team.getGoals() > selectedTeam.getGoals())
                        selectedTeam = team;
                }
            }
            selectedTeam.setRank(i+1);
            ranking.add(selectedTeam);
            teams.remove(selectedTeam);
            selectedTeam = dummyTeam;

        }

        return ranking;
    }

    /**
     * Gibt eine nicht geordnete Liste aller TeamStatistiken zurück
     *
     * @return List<TeamStatistics>
     *                  Eine ungeordnete Liste aller TeamStatistiken
     */
    public List<TeamStatistics> getAllStats() {
        return repository.findAll();
    }

    /**
     * Gibt eine TeamStatistk nach ihrer ID zurück
     *
     * @param id
     *                  Die ID des Teams
     *
     * @return TeamStatistics
     *                  Eine TeamStatistik
     */
    public TeamStatistics getTeamStatsById(Long id) {
        if(!repository.existsById(id)){
            throw new IllegalStateException("Match with id "+ id +" could not be found");
        }

        return repository.getById(id);
    }

    /**
     * Gibt das beste aller Teams zurück
     *
     * @return TeamStatistics
     *                  Eine TeamStatistik für das beste Team
     */
    public TeamStatistics getBestTeam() {
       List<TeamStatistics> teamsRanked = getRanking();
       return teamsRanked.get(0);
    }

    /**
     * Gibt das schlechteste aller Teams zurück
     *
     * @return TeamStatistics
     *                  Eine TeamStatistik für das schlechteste Team
     */
    public TeamStatistics getWorstTeam() {
        List<TeamStatistics> teamsRanked = getRanking();
        return teamsRanked.get(teamsRanked.size()-1);
    }


    /**
     * Erzeugt oder Aktualisiert die TeamStatistik um ein weiteres Match
     *
     * @param match
     *                  Eine Match, dass der Teamstatistik hinzugefügt werden soll
     */
    public void addMatchStatistic(Matches match){
        String team1 = match.getTeam1();
        String team2 = match.getTeam2();
        TeamStatistics statsTeam1 = getTeamByName(team1);
        TeamStatistics statsTeam2 = getTeamByName(team2);


        int[] points = calculateMatchpoints(match);

        if(statsTeam1==null){
            statsTeam1 = new TeamStatistics(team1, 1, match.getTeam1Goals(), match.getTeam2Goals(), points[0]);
        }else{
            statsTeam1 = updateTeamStats(statsTeam1, match.getTeam1Goals(), match.getTeam2Goals(), points[0]);
            statsTeam1.setGames(statsTeam1.getGames()+1);
        }


        if(statsTeam2==null){
            statsTeam2 = new TeamStatistics(team2, 1, match.getTeam2Goals(), match.getTeam1Goals(), points[1]);

        }else{
            statsTeam2 = updateTeamStats(statsTeam2, match.getTeam2Goals(), match.getTeam1Goals(), points[1]);
            statsTeam2.setGames(statsTeam2.getGames()+1);

        }
        repository.save(statsTeam1);
        repository.save(statsTeam2);

    }

    /**
     * Entfernt eine MatchStatistic
     *
     * @param match
     *                  Eine Match, dass aus der Teamstatistik wieder entfernt werden soll
     */

    public void removeMatchStatistic(Matches match){
        String team1 = match.getTeam1();
        String team2 = match.getTeam2();
        TeamStatistics statsTeam1 = getTeamByName(team1);
        TeamStatistics statsTeam2 = getTeamByName(team2);

        int[] points = calculateMatchpoints(match);

        statsTeam1 = updateTeamStats(statsTeam1, -match.getTeam1Goals(), -match.getTeam2Goals(), -points[0]);
        statsTeam1.setGames(statsTeam1.getGames()-1);
        statsTeam2 = updateTeamStats(statsTeam2, -match.getTeam2Goals(), -match.getTeam1Goals(), -points[1]);
        statsTeam2.setGames(statsTeam2.getGames()-1);
        repository.save(statsTeam1);
        repository.save(statsTeam2);

    }

    //Private Methods for Internal Use only

    private TeamStatistics updateTeamStats(TeamStatistics team, int goalsInMatch, int goalsAgainstInMatch, int points) {
        team.setGoalsAgainst(team.getGoalsAgainst()+ goalsAgainstInMatch);
        team.setGoals(team.getGoals()+ goalsInMatch);
        team.setPoints(team.getPoints()+points);
        team.setGoalDifference(team.getGoals()-team.getGoalsAgainst());
        return team;
    }

    private TeamStatistics getTeamByName(String name){
        TeamStatistics stats = repository.findTeamStatisticsByTeamName(name);

        if(stats == null){
            return null;
        }
        return stats;
    }

    private int[] calculateMatchpoints(Matches match){

        int[] points = new int[2];
        int pointsTeam1 = 0;
        int pointsTeam2 = 0;
        if(match.getTeam1Goals()>match.getTeam2Goals()){
            pointsTeam1 = 3;
        }else if(match.getTeam1Goals()== match.getTeam2Goals()){
            pointsTeam1 = 1;
            pointsTeam2 = 1;
        }else{
            pointsTeam2 = 3;
        }
        points[0]=pointsTeam1;
        points[1]=pointsTeam2;

        return points;

    }
}
