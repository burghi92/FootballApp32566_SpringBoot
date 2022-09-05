package de.burkhart.footballapp32566.data;

import de.burkhart.footballapp32566.service.TeamStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface zum Auruf von DB-Methoden zum Anlegen, Bearbeiten und Löschen einer TeamStatisitk
 * Erbt von JPARepository
 *
 * @author André Burkhart, 32566
 *
 */


@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStatistics, Long> {

    /**
     * Methode um die Statistik über einen Teamnamen aufzurufen
     *
     * @param name
     *                  Name des Teams
     * @return TeamStatistics
     *                  Die Teamstatistik
     */
    TeamStatistics findTeamStatisticsByTeamName(String name);
}
