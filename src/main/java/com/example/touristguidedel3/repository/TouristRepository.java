package com.example.touristguidedel3.repository;


import com.example.touristguidedel3.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    ArrayList<TouristAttraction> attractions = new ArrayList<>();

    // Metode til at retunere en attraktion //
    public ArrayList<TouristAttraction> getAttractions() {
        return attractions;
    }

    public TouristAttraction findAttractionByName(String name) {
        for (TouristAttraction touristAttraction : attractions) {
            if (touristAttraction.getName().equalsIgnoreCase(name)) {
                return touristAttraction;
            }
        }
        return null;
    }

    // Metode til at kunne tilføje attraktioner //
    public void saveAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
    }


    public void updateAttraction(TouristAttraction attraction) {
        TouristAttraction existingAttraction = findAttractionByName(attraction.getName());
        if (existingAttraction != null) {
            existingAttraction.setDescription(attraction.getDescription());
            existingAttraction.setLocation(attraction.getLocation());
            existingAttraction.setTags(attraction.getTags());
        }
    }

    public void deleteAttraction(String nameOfAttraction) {
        TouristAttraction attraction = findAttractionByName(nameOfAttraction);
        if (attraction != null) {
            attractions.remove(attraction);
        }
    }

    // Hardkodet liste med byer //
    public List<String> getCities() {
        return List.of(
                "København",
                "Roskilde",
                "Helsingør",
                "Næstved",
                "Køge",
                "Slagelse",
                "Holbæk",
                "Kalundborg",
                "Hillerød",
                "Vordingborg"
        );
    }


    public List<String> getTags() {
        return List.of(Category.values());
    }


}
