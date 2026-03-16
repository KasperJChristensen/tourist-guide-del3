package com.example.touristguidedel3.repository;


import com.example.touristguidedel3.model.Category;
import com.example.touristguidedel3.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    ArrayList<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository() {
        attractions();
    }


    // Hardkodet attraktioner //
    private void attractions() {
        attractions.add(new TouristAttraction(
                "Tivoli",
                "About a 2-minute walk from Copenhagen Central Station you will find Tivoli. " +
                        "A historic amusement park founded in 1843, known for its atmosphere, " +
                        "amusement rides and the world's oldest roller coaster.",
                "Copenhagen",
                List.of(Category.CULTURE, Category.SIGHTSEEING, Category.FAMILY_FRIENDLY)
        ));

        attractions.add(new TouristAttraction(
                "Strøget",
                "Strøget starts at Copenhagen's Town Hall Square and stretches for about 1 km through the " +
                        "city center to Kongens Nytorv. It is one of Europe's longest pedestrian streets.",
                "Copenhagen – Kongens Nytorv",
                List.of(Category.CULTURE, Category.SHOPPING, Category.SIGHTSEEING)
        ));

        attractions.add(new TouristAttraction(
                "The King's Garden",
                "The King's Garden is Denmark's oldest royal garden and is located at Rosenborg Castle. " +
                        "A popular green haven with historic surroundings in the middle of Copenhagen.",
                "Copenhagen",
                List.of(Category.CULTURE, Category.NATURE, Category.SIGHTSEEING, Category.FAMILY_FRIENDLY)
        ));

        attractions.add(new TouristAttraction(
                "The Viking Ship Museum",
                "With the sound of the waves and smell of wet wood, the Viking Ship Museum is located right by Roskilde Fjord and it is a great visit for anyone with Viking blood in their veins." +
                        "Here you have plenty of activities, history, atmosphere and beautiful harbor - you can even go sailing.",
                "Roskilde",
                List.of(Category.CULTURE, Category.FAMILY_FRIENDLY, Category.HISTORY)
        ));
    }

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


    public List<Category> getTags() {
        return List.of(Category.values());
    }


}
