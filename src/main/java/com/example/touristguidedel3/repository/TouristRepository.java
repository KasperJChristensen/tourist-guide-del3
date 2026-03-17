package com.example.touristguidedel3.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.example.touristguidedel3.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<TouristAttraction> rowMapper = (rs, rowNum) -> {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setName(rs.getString("name"));
        attraction.setDescription(rs.getString("description"));
        attraction.setLocation(rs.getString("location"));

        String tags = rs.getString("tags");
        attraction.setTags(tags != null
                ? Arrays.stream(tags.split(",")).map(String::trim).toList()
                : List.of());

        return attraction;
    };

    public List<TouristAttraction> findAllAtrractions() {
        return jdbcTemplate.query("SELECT * FROM tourist_attractions", rowMapper);
    }
}






//    // Metode til at kunne tilføje attraktioner //
//    public void saveAttraction(TouristAttraction attraction) {
//        attractions.add(attraction);
//    }
//
//
//    public void updateAttraction(TouristAttraction attraction) {
//        TouristAttraction existingAttraction = findAttractionByName(attraction.getName());
//        if (existingAttraction != null) {
//            existingAttraction.setDescription(attraction.getDescription());
//            existingAttraction.setLocation(attraction.getLocation());
//            existingAttraction.setTags(attraction.getTags());
//        }
//    }
//
//    public void deleteAttraction(String nameOfAttraction) {
//        TouristAttraction attraction = findAttractionByName(nameOfAttraction);
//        if (attraction != null) {
//            attractions.remove(attraction);
//        }
//    }
//
//    // Hardkodet liste med byer //
//    public List<String> getCities() {
//        return List.of(
//                "København",
//                "Roskilde",
//                "Helsingør",
//                "Næstved",
//                "Køge",
//                "Slagelse",
//                "Holbæk",
//                "Kalundborg",
//                "Hillerød",
//                "Vordingborg"
//        );
//    }
//
//
//    public List<String> getTags() {
//        return List.of(Category.values());
//    }


