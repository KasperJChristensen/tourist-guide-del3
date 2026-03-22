package com.example.touristguidedel3.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.example.touristguidedel3.model.TouristAttraction;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Arrays;
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
    }

    public boolean deleteAttraction(String name) {
        String sql = """
                DELETE FROM attraction
                WHERE attraction.attraction_name = ?
                """;

        int rowsDeleted = jdbcTemplate.update(sql, name);
        return rowsDeleted > 0;
    }

    public int findLocationId(String cityName) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM location WHERE city_name = ?",
                Integer.class,
                cityName
        );
    }

    // Metode til at kunne tilføje attraktioner //
    public int saveAttraction(TouristAttraction attraction, int locationId) {
        String sql = "INSERT INTO attraction (attraction_name, description, location_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                sql,
                attraction.getName(),
                attraction.getDescription(),
                locationId
        );


        int attractionId = jdbcTemplate.queryForObject(
                "SELECT id FROM attraction WHERE attraction_name = ?",
                Integer.class,
                attraction.getName());

        return attractionId;


//        Version fra Exceptional_Profile

//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        String sql = "INSERT INTO attraction (attraction_name, description, location_id) VALUES (?, ?, ?)";
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, attraction.getName());
//            ps.setString(2, attraction.getDescription());
//            ps.setInt(3, locationId);
//            return ps;
//        }, keyHolder);
//
//        return keyHolder.getKey().intValue();
    }

    public void saveAttraction_tags(int attractionId, List<String> tags) {
        String sql = "INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (?, ?)";

        for (String tag : tags) {
            int tagId = jdbcTemplate.queryForObject(
                    "SELECT id FROM tags WHERE tag = ?",
                    Integer.class,
                    tag
            );
            jdbcTemplate.update(sql, attractionId, tagId);
        }
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
