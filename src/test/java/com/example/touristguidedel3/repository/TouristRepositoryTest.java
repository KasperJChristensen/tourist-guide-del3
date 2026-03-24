package com.example.touristguidedel3.repository;

import com.example.touristguidedel3.model.TouristAttraction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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
    }

    @Test
    void findLocationId() {

    }

    @Test
    void saveAttraction() {
        repo.saveAttraction(new TouristAttraction(3, "Rundetårn", "Et højt tårn i København", "København V", List.of("Culture", "History", "Tourism")), 1);

        TouristAttraction rundetårn = repo.findAttractionById(3);


        assertThat(rundetårn).isNotNull();
        assertThat(rundetårn.getName()).isEqualTo("Rundetårn");
        assertThat(rundetårn.getDescription()).isEqualTo("Et højt tårn i København");
        assertThat(rundetårn.getLocation()).isEqualTo("København V");

    }

    @Test
    void saveAttraction_tags() {
    }

    @Test
    void updateAttraction() {
    }

    @Test
    void deleteTagsForAttraction() {
    }

    @Test
    void deleteAttraction() {
    }

    @Test
    void getCities() {
    }

    @Test
    void getTags() {
    }
}