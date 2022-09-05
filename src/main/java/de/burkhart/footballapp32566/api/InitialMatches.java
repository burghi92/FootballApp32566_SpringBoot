package de.burkhart.footballapp32566.api;

import de.burkhart.footballapp32566.service.Matches;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialMatches {

    @Bean
    CommandLineRunner commandLIneRunner(MatchController controller) {
        return args -> {
            Matches match1 = new Matches("Vfb Stuttgart", 2, "1. FC. Köln", 1);
            Matches match2 = new Matches("Borussia Dortmund", 2, "Hertha BSC", 1);
            Matches match3 = new Matches("Borussia Mönchengladbach", 5, "TSG 1899 Hoffenheim", 1);
            controller.addNewMatch(match1);
            controller.addNewMatch(match2);
            controller.addNewMatch(match3);
        };
    }
}
