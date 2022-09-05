package de.burkhart.footballapp32566.data;

import de.burkhart.footballapp32566.service.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



/**
 * Repository Interface zum Auruf von DB-Methoden zum Anlegen, Bearbeiten und Löschen von Matches
 * Erbt von JPARepository
 *
 * @author André Burkhart, 32566
 *
 */

@Repository
public interface MatchRepository extends JpaRepository<Matches, Long> {


    /**
     * Methode um ein Match mit den Parametern des Heim und Auswärtsteams zu finden
     *
     * @param team1
     *                  Name des HeimTeams
     * @param team2
     *                  Name des Auswärtsteams
     * @return Optional<Matches>
     *                  Ein Optional aus Matches
     */
    Optional<Matches> findMatchesByTeam1AndTeam2(String team1, String team2);

}
