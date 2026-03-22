package com.example.touristguidedel3.model;

import java.util.List;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private String location;
    private List<String> tags;

    public TouristAttraction(int id, String name, String description, String location, List<String> tags) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags;
        this.id = id;
    }

    public TouristAttraction() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}


