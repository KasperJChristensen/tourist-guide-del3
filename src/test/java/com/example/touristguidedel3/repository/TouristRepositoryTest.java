package com.example.touristguidedel3.repository;

import com.example.touristguidedel3.model.TouristAttraction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class TouristRepositoryTest {

    @Autowired
    private TouristRepository repo;

    @Test
    void getAttractions() {
        List<TouristAttraction> all = repo.getAttractions();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getName()).isEqualTo("Tivoli Gardens");
        assertThat(all.get(1).getName()).isEqualTo("The Viking Ship Museum");

    }


    @Test
    void findAttractionById() {
        TouristAttraction attraction = repo.findAttractionById(1);

        assertThat(attraction).isNotNull();
        assertThat(attraction.getName()).isEqualTo("Tivoli Gardens");
        assertThat(attraction.getDescription()).isEqualTo("An amusement park in the center of Copenhagen");
        assertThat(attraction.getLocation()).isEqualTo("København V");

    }

    @Test
    void findLocationId() {
        List<String> allLocations = repo.getCities();

        assertThat(allLocations).isNotNull();
        assertThat(allLocations.size()).isEqualTo(3);
        assertThat(allLocations.get(0)).isEqualTo("København V");
        assertThat(allLocations.get(1)).isEqualTo("Køge");
        assertThat(allLocations.get(2)).isEqualTo("Roskilde");

    }

    @Test
    void saveAttraction() {
        int id = repo.saveAttraction(new TouristAttraction(4, "Rundetårn", "Et højt tårn i København", "København V", List.of("Culture", "History", "Tourism")), 1);

        TouristAttraction rundetårn = repo.findAttractionById(id);


        assertThat(rundetårn).isNotNull();
        assertThat(rundetårn.getName()).isEqualTo("Rundetårn");
        assertThat(rundetårn.getDescription()).isEqualTo("Et højt tårn i København");
        assertThat(rundetårn.getLocation()).isEqualTo("København V");

    }

    @Test
    void saveAttraction_tags() {
        repo.saveAttraction_tags(1, List.of("History"));

        assertThat(repo.findAttractionById(1).getTags()).isEqualTo(List.of("Culture", "History"));

    }

    @Test
    void updateAttraction() {
        TouristAttraction rundetårn = new TouristAttraction(1, "Rundetårn", "Et højt tårn i København", "København V", List.of("Culture", "History", "Tourism"));
        repo.updateAttraction(rundetårn,1);

        assertThat(rundetårn.getName()).isEqualTo(repo.findAttractionById(1).getName());
        assertThat(rundetårn.getDescription()).isEqualTo(repo.findAttractionById(1).getDescription());
        assertThat(rundetårn.getLocation()).isEqualTo(repo.findAttractionById(1).getLocation());

    }

    @Test
    void deleteTagsForAttraction() {

    }

    @Test
    void deleteAttraction() {
    }

    @Test
    void getCities() {
        List<String> all = repo.getCities();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(3);

        assertThat(all.get(0)).isEqualTo("København V");
        assertThat(all.get(1)).isEqualTo("Køge");
        assertThat(all.get(2)).isEqualTo("Roskilde");
    }

    @Test
    void getTags() {
        List<String> all = repo.getTags();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0)).isEqualTo("Culture");
        assertThat(all.get(1)).isEqualTo("History");
    }
}