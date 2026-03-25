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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertThat(attraction.getTags()).containsExactly("Culture");
    }

    @Test
    void findById_throwsWhenMissing() {
        assertThrows(Exception.class, () -> repo.findAttractionById(999));
    }

    @Test
    void findLocationId() {
        assertThat(repo.findLocationId("Køge")).isEqualTo(2);
    }

    @Test
    void saveAttraction() {
        TouristAttraction t = new TouristAttraction(
                0,
                "Rundetårn",
                "Et højt tårn i København",
                "København V",
                List.of("Culture", "History")
        );

        int id = repo.saveAttraction(t, 1);
        repo.saveAttraction_tags(id, t.getTags());

        TouristAttraction saved = repo.findAttractionById(id);

        assertThat(saved.getName()).isEqualTo("Rundetårn");
        assertThat(saved.getTags()).containsExactlyInAnyOrder("Culture", "History");
    }

    @Test
    void saveAttraction_tags() {
        repo.saveAttraction_tags(1, List.of("History"));

        TouristAttraction a = repo.findAttractionById(1);

        assertThat(a.getTags()).containsExactlyInAnyOrder("Culture", "History");
    }

    @Test
    void updateAttraction() {
        TouristAttraction rundetårn = new TouristAttraction(1, "Rundetårn", "Et højt tårn i København", "København V", List.of("Culture", "History", "Tourism"));
        repo.updateAttraction(rundetårn, 1);

        assertThat(rundetårn.getName()).isEqualTo(repo.findAttractionById(1).getName());
        assertThat(rundetårn.getDescription()).isEqualTo(repo.findAttractionById(1).getDescription());
        assertThat(rundetårn.getLocation()).isEqualTo(repo.findAttractionById(1).getLocation());
    }

    @Test
    void deleteTagsForAttraction() {
        repo.deleteTagsForAttraction(1);

        TouristAttraction attraction = repo.findAttractionById(1);

        assertThat(attraction.getTags()).isEmpty();
    }

    @Test
    void deleteAttraction() {
        repo.deleteAttraction(1);

        assertThrows(Exception.class, () -> repo.findAttractionById(1));

        assertThat(repo.findAttractionById(2)).isNotNull();
    }

    @Test
    void getCities() {
        List<String> all = repo.getCities();

        assertThat(all).containsExactlyInAnyOrder("København V", "Køge", "Roskilde");
    }

    @Test
    void getTags() {
        List<String> all = repo.getTags();

        assertThat(all).containsExactlyInAnyOrder("Culture", "History");
    }
}