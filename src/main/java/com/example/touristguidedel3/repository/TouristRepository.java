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

        String tags = rs.getString("tag");
        attraction.setTags(tags != null
                ? Arrays.stream(tags.split(",")).map(String::trim).toList()
                : List.of());

        return attraction;
    };

    public List<TouristAttraction> getAttractions() {
        return jdbcTemplate.query(
                "SELECT attraction.attraction_name, attraction.description, attraction.city_name, attraction_tag.tag " +
                         "FROM attraction " +
                         "join attraction_tag " +
                         "on attraction.attraction_name = attraction_tag.attraction_name;",
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
