package com.example.touristguidedel3.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.example.touristguidedel3.model.TouristAttraction;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String sql = """
                SELECT attraction.attraction_name, attraction.description, location.city_name, GROUP_CONCAT(tags.tag ORDER BY tags.tag SEPARATOR ',') AS tags 
                FROM attraction
                JOIN location
                    ON attraction.location_id = location.id 
                left JOIN attraction_tag 
                    ON attraction.id = attraction_tag.attraction_id 
                left JOIN tags 
                    ON attraction_tag.tag_id = tags.id 
                GROUP BY attraction.id, attraction.attraction_name, attraction.description, location.city_name;
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public TouristAttraction findAttractionByName(String name) {
        String sql = """
                SELECT attraction.attraction_name, attraction.description, location.city_name, GROUP_CONCAT(tags.tag ORDER BY tags.tag SEPARATOR ',') AS tags
                FROM attraction 
                JOIN location 
                    ON attraction.location_id = location.id 
                left JOIN attraction_tag 
                    ON attraction.id = attraction_tag.attraction_id 
                left JOIN tags 
                    ON attraction_tag.tag_id = tags.id 
                WHERE attraction.attraction_name = ? 
                GROUP BY attraction.id, attraction.attraction_name, attraction.description, location.city_name;
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, name);

//        for (TouristAttraction touristAttraction : attractions) {
//            if (touristAttraction.getName().equalsIgnoreCase(name)) {
//                return touristAttraction;
//            }
//        }
//        return null;
    }

    public boolean deleteAttraction(String name) {
        String sql = """
                DELETE FROM attraction
                WHERE attraction.attraction_name = ?
                """;

        int rowsDeleted = jdbcTemplate.update(sql, name);
        return rowsDeleted > 0;
    }


    // Metode til at kunne tilføje attraktioner //
    public void saveAttraction(TouristAttraction attraction) {
        int locationId = jdbcTemplate.queryForObject(
                "SELECT id FROM location WHERE city_name = ?",
                Integer.class,
                attraction.getLocation());

        String sql = "INSERT INTO attraction (attraction_name, description, location_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                sql,
                attraction.getName(),
                attraction.getDescription(),
                locationId
        );

//        String sql = "INSERT INTO attraction (attraction_name, description, location_id) VALUES (?, ?, ?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, attraction.getName());
//            ps.setString(2, attraction.getDescription());
//            ps.setString(3, attraction.getLocation());
//
//            return ps;
//        }, keyHolder);
//
//        Number key = keyHolder.getKey();
//        if (key == null) {
//            throw new IllegalStateException("Failed to retrieve generated key");
//        }
//
//        return new TouristAttraction(key.intValue(), attraction.getName(), attraction.getDescription(), attraction.getLocation());
    }

    public boolean updateAttraction(TouristAttraction attraction) {
        String sql = "UPDATE attraction SET attraction_name = ?, description = ?, location_id = ? WHERE attraction_id = ?";
        int rowsUpdated = jdbcTemplate.update(
                sql,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getLocation()
        );
        return rowsUpdated > 0;
    }

    public boolean deleteAttractionById(int id) {
        String sql = """
                DELETE FROM attraction
                WHERE id = ?
                """;
        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }


    public List<String> getCities() {
        String sql = "SELECT location.city_name FROM location";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<String> getTags() {
        String sql = "SELECT tags.tag FROM tags";
        return jdbcTemplate.queryForList(sql, String.class);
    }


}
