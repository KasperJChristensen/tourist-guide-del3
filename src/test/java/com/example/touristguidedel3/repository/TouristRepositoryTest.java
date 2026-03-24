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

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class TouristRepositoryTest {

    @Autowired
    private TouristRepository repo;

    @Test
    void readAll() {
        List<TouristAttraction> all = repo.getAttractions();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(9);
        assertThat(all.get(0).getName()).isEqualTo("Tivoli Gardens");
        assertThat(all.get(1).getName()).isEqualTo("Nyhavn");
        assertThat(all.get(2).getName()).isEqualTo("The Round Tower");
        assertThat(all.get(3).getName()).isEqualTo("Frederiksberg Gardens");
        assertThat(all.get(4).getName()).isEqualTo("ARoS Aarhus Art Museum");
        assertThat(all.get(5).getName()).isEqualTo("City Hall Square");
        assertThat(all.get(6).getName()).isEqualTo("The Viking Ship Museum");
        assertThat(all.get(7).getName()).isEqualTo("The Kings Garden");
        assertThat(all.get(8).getName()).isEqualTo("Strøget");
    }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAttractions() {
    }

    @Test
    void findAttractionById() {
    }

    @Test
    void findLocationId() {
    }

    @Test
    void saveAttraction() {
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