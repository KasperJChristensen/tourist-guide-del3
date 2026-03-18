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
        attraction.setName(rs.getString("attraction_name"));
        attraction.setDescription(rs.getString("description"));
        attraction.setLocation(rs.getString("city_name"));

        String tags = rs.getString("tags");
        attraction.setTags(tags != null
                ? Arrays.asList(tags.split(","))
                : List.of());

        return attraction;
    };

    public List<TouristAttraction> getAttractions() {
        return jdbcTemplate.query(
                "SELECT attraction.attraction_name, attraction.description, location.city_name, GROUP_CONCAT(tags.tag ORDER BY tags.tag SEPARATOR ',') AS tags " +
                        "FROM attraction " +
                        "JOIN location " +
                        "    ON attraction.location_id = location.id " +
                        "left JOIN attraction_tag " +
                        "    ON attraction.id = attraction_tag.attraction_id " +
                        "left JOIN tags " +
                        "    ON attraction_tag.tag_id = tags.id " +
                        "GROUP BY attraction.id, attraction.attraction_name, attraction.description, location.city_name\n;",
                rowMapper
        );
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


}
