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
                        "GROUP BY attraction.id, attraction.attraction_name, attraction.description, location.city_name;",
                rowMapper
        );
    }

    public TouristAttraction findAttractionByName(String name) {
        String sql = "SELECT attraction.attraction_name, attraction.description, location.city_name, GROUP_CONCAT(tags.tag ORDER BY tags.tag SEPARATOR ',') AS tags " +
                "FROM attraction " +
                "JOIN location " +
                "    ON attraction.location_id = location.id " +
                "left JOIN attraction_tag " +
                "    ON attraction.id = attraction_tag.attraction_id " +
                "left JOIN tags " +
                "    ON attraction_tag.tag_id = tags.id " +
                "WHERE attraction.attraction_name = ? " +
                "GROUP BY attraction.id, attraction.attraction_name, attraction.description, location.city_name;";

        return jdbcTemplate.queryForObject(sql ,rowMapper, name);

//        for (TouristAttraction touristAttraction : attractions) {
//            if (touristAttraction.getName().equalsIgnoreCase(name)) {
//                return touristAttraction;
//            }
//        }
//        return null;
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

    public List<String> getCities() {
        String sql = "SELECT location.city_name FROM location";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getString("city_name")
        );
    }

    public List<String> getTags() {
        String sql = "SELECT tags.tag FROM tags";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getString("tag")
        );
    }


}
