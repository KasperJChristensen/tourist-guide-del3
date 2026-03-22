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
        attraction.setId(rs.getInt("id"));
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
                SELECT attraction.id, attraction.attraction_name, attraction.description, location.city_name, GROUP_CONCAT(tags.tag ORDER BY tags.tag SEPARATOR ',') AS tags 
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

    public TouristAttraction findAttractionById(int id) {
        String sql = """
                SELECT attraction.id, attraction.attraction_name, attraction.description, location.city_name, GROUP_CONCAT(tags.tag ORDER BY tags.tag SEPARATOR ',') AS tags
                FROM attraction 
                JOIN location 
                    ON attraction.location_id = location.id 
                left JOIN attraction_tag 
                    ON attraction.id = attraction_tag.attraction_id 
                left JOIN tags 
                    ON attraction_tag.tag_id = tags.id 
                WHERE attraction.id = ? 
                GROUP BY attraction.id, attraction.attraction_name, attraction.description, location.city_name;
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
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


    public void updateAttraction(TouristAttraction attraction, int locationId) {
        String sql = "UPDATE attraction SET attraction_name = ?, description = ?, location_id = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                attraction.getName(),
                attraction.getDescription(),
                locationId,
                attraction.getId()
        );
    }

    public void deleteTagsForAttraction(int attractionId){
        String sql = """
                DELETE FROM attraction_tag
                WHERE attraction_id = ?
                """;
        jdbcTemplate.update(sql, attractionId);
    }

    public void deleteAttraction(int id) {
        String sql = """
                DELETE FROM attraction
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
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
